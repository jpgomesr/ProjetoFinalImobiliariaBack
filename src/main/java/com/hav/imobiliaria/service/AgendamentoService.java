package com.hav.imobiliaria.service;

import com.hav.imobiliaria.controller.dto.agendamento.AgendamentoPostDto;
import com.hav.imobiliaria.controller.dto.agendamento.AgendamentoPutDTO;
import com.hav.imobiliaria.exceptions.AcessoNegadoException;
import com.hav.imobiliaria.exceptions.requisicao_padrao.AgendamentoComImovelFinalizadoException;
import com.hav.imobiliaria.exceptions.requisicao_padrao.AgendamentoInexistenteException;
import com.hav.imobiliaria.exceptions.requisicao_padrao.TipoUsuarioIncorretoException;
import com.hav.imobiliaria.model.entity.*;
import com.hav.imobiliaria.model.enums.RoleEnum;
import com.hav.imobiliaria.model.enums.StatusAgendamentoEnum;
import com.hav.imobiliaria.model.enums.TipoBunnerEnum;
import com.hav.imobiliaria.model.enums.TipoEmailEnum;
import com.hav.imobiliaria.repository.AgendamentoRepository;
import com.hav.imobiliaria.repository.specs.AgendamentoSpecs;
import com.hav.imobiliaria.security.utils.SecurityUtils;
import com.hav.imobiliaria.validator.AgendamentoValidator;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AgendamentoService {

    private final AgendamentoRepository repository;
    private final UsuarioService usuarioService;
    private final EmailService emailService;
    private final ImovelService imovelService;
    private final AgendamentoValidator validator;
    private final NotificacaoService notificacaoService;
    @Value("${FRONTEND_URL}")
    private  String FRONTEND_URL;


    @Transactional
    public void salvarAgendamento(AgendamentoPostDto agendamentoPostDto) {

        Agendamento agendamento = new Agendamento();

        validator.validarUsuarios(agendamento,agendamentoPostDto.idUsuario(), agendamentoPostDto.idCorretor());
        validator.validarCriacaoAgendamento(agendamentoPostDto, agendamento.getUsuarioComum());


        agendamento.setImovel(imovelService.buscarPorId(agendamentoPostDto.idImovel()));
        agendamento.getCorretor().removerHorarioPorDatahora(agendamentoPostDto.dataHora());
        agendamento.setDataHora(agendamentoPostDto.dataHora());

        repository.save(agendamento);

        Map<String, Object> variables = new HashMap<>();


        variables.put("nomeCliente", agendamento.getCorretor().getNome());
        variables.put("titulo", "Nova solicitação de agendamento");
        variables.put("mensagem", "O usuário " + agendamento.getUsuarioComum().getNome() + " fez uma solicitação de agendamento para você");
        variables.put("linkAcao", FRONTEND_URL + "/historico-agendamentos/" +agendamento.getCorretor().getId());
        variables.put("textoBotao", "Ir para os seus agendametos");

        EmailRequest emailRequest = EmailRequest.builder()
                .tipoEmail(TipoEmailEnum.NOTIFICACAO_IMOBILIARIA)
                .destinatario(agendamento.getCorretor().getEmail())
                .variaveis(variables).build();

        Notificacao notificacao = new Notificacao();
        notificacao.setLink( FRONTEND_URL +"/historico-agendamentos/" +agendamento.getCorretor().getId());
        notificacao.setTitulo("Novo agendamento");
        notificacao.setDescricao("Há um novo agendamento para você");
        notificacaoService.criarNotificacao(notificacao,agendamento.getCorretor().getId());
        emailService.enviarEmail(emailRequest);
    }
    @Transactional
    public void atualizarAgendamento(AgendamentoPutDTO agendamentoPutDTO) {

        Imovel imovel = imovelService.buscarPorId(agendamentoPutDTO.idImovel());


        Agendamento agendamento = repository.findById(agendamentoPutDTO.id()).orElseThrow(AgendamentoInexistenteException::new);

        if(imovel.getBanner() != null && imovel.getBanner()){
            if(imovel.getTipoBanner().equals(TipoBunnerEnum.ADQUIRIDO) ||
                    imovel.getTipoBanner().equals(TipoBunnerEnum.ALUGADO)){
                agendamento.setStatus(StatusAgendamentoEnum.CANCELADO);
                repository.save(agendamento);
                repository.flush();
                throw new AgendamentoComImovelFinalizadoException();
            }
        }

        validator.validarUsuarios(agendamento,agendamentoPutDTO.idUsuario(), agendamentoPutDTO.idCorretor());


        validator.validarAtualizacaoAgendamento(agendamentoPutDTO, agendamento.getUsuarioComum());

        agendamento.setImovel(imovelService.buscarPorId(agendamentoPutDTO.idImovel()));

        agendamento.getCorretor().removerHorarioPorDatahora(agendamentoPutDTO.dataHora());
        agendamento.setDataHora(agendamentoPutDTO.dataHora());
        agendamento.setStatus(StatusAgendamentoEnum.PENDENTE);

        repository.save(agendamento);

        Map<String, Object> variables = new HashMap<>();


        variables.put("nomeCliente", agendamento.getCorretor().getNome());
        variables.put("titulo", "Reagendamento de visita");
        variables.put("mensagem", "O usuário " + agendamento.getUsuarioComum().getNome() + " fez uma solicitação de reagendamento de visita para você");
        variables.put("linkAcao", FRONTEND_URL + "/historico-agendamentos/" +agendamento.getCorretor().getId());
        variables.put("textoBotao", "Ir para os seus agendametos");

        EmailRequest emailRequest = EmailRequest.builder()
                .tipoEmail(TipoEmailEnum.NOTIFICACAO_IMOBILIARIA)
                .destinatario(agendamento.getCorretor().getEmail())
                .variaveis(variables).build();


        emailService.enviarEmail(emailRequest);



    }

    public Page<Agendamento> listarAgendamentosCorretorId(Pageable pageable,
                                                          Long idCorretor,
                                                          StatusAgendamentoEnum statusAgendamento,
                                                          LocalDate data) {

        Specification<Agendamento> specs = criandoSpecs(statusAgendamento,data);
        specs = specs.and(AgendamentoSpecs.idCorretorEquals(idCorretor));




       return repository.findAll(specs,pageable);
    }
    public Page<Agendamento> listarAgendamentosUsuarioId(Pageable pageable,
                                                          Long idUsuario,
                                                          StatusAgendamentoEnum statusAgendamento,
                                                          LocalDate data) {

        Specification<Agendamento> specs = criandoSpecs(statusAgendamento,data);

        specs = specs.and(AgendamentoSpecs.idUsuarioEquals(idUsuario));

        return repository.findAll(specs,pageable);
    }
    private Specification<Agendamento> criandoSpecs(StatusAgendamentoEnum status, LocalDate data) {

        Specification<Agendamento> specs = Specification.where((root, query, criteriaBuilder) -> criteriaBuilder.conjunction());


        if(status != null){
            specs = specs.and(AgendamentoSpecs.statusEquals(status));
        }
        if(data != null){
            specs = specs.and(AgendamentoSpecs.dataEquals(data));
        }

        return  specs;
    }


    public void atualizarStatus(Long id, StatusAgendamentoEnum status) {

        if(!this.repository.existsById(id)){
            throw new AgendamentoInexistenteException();
        }
        if(status.equals(StatusAgendamentoEnum.CONFIRMADO) && !SecurityUtils.buscarUsuarioLogado().getRole().equals(RoleEnum.CORRETOR)){
            throw  new AcessoNegadoException();
        }



        repository.findById(id).ifPresent(agendamento -> {

            agendamento.setStatus(status);

            if(SecurityUtils.buscarUsuarioLogado().getRole().equals(RoleEnum.CORRETOR)){
                Map<String, Object> variables = new HashMap<>();


                System.out.println(FRONTEND_URL);
                variables.put("nomeCliente", agendamento.getUsuarioComum().getNome());
                variables.put("titulo", "Mudança de estádo de agendamento");
                variables.put("mensagem", "O corretor acabou de alterar o status do seu agendamento para "+ agendamento.getStatus());
                variables.put("linkAcao", FRONTEND_URL + "/historico-agendamentos/" +agendamento.getUsuarioComum().getId());
                variables.put("textoBotao", "Ir para os seus agendametos");

                EmailRequest emailRequest = EmailRequest.builder()
                        .tipoEmail(TipoEmailEnum.NOTIFICACAO_IMOBILIARIA)
                        .destinatario(agendamento.getUsuarioComum().getEmail())
                        .variaveis(variables).build();


                emailService.enviarEmail(emailRequest);

            }

            repository.save(agendamento);
        });



    }
}
