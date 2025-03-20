package com.hav.imobiliaria.service;

import com.hav.imobiliaria.controller.dto.agendamento.AgendamentoPostDto;
import com.hav.imobiliaria.exceptions.requisicao_padrao.AgendamentoInexistenteException;
import com.hav.imobiliaria.exceptions.requisicao_padrao.TipoUsuarioIncorretoException;
import com.hav.imobiliaria.model.entity.Agendamento;
import com.hav.imobiliaria.model.entity.Corretor;
import com.hav.imobiliaria.model.entity.Usuario;
import com.hav.imobiliaria.model.entity.UsuarioComum;
import com.hav.imobiliaria.model.enums.RoleEnum;
import com.hav.imobiliaria.model.enums.StatusAgendamentoEnum;
import com.hav.imobiliaria.repository.AgendamentoRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AgendamentoService {

    private final AgendamentoRepository repository;
    private final UsuarioService usuarioService;
    private final ImovelService imovelService;

    @Transactional
    public void salvarAgendamento(AgendamentoPostDto agendamentoPostDto) {

        Agendamento agendamento = new Agendamento();

        Usuario corretor = usuarioService.buscarPorId(agendamentoPostDto.idCorretor());
        Usuario usuarioComum = usuarioService.buscarPorId(agendamentoPostDto.idUsuario());

        if(corretor.getRole().equals(RoleEnum.CORRETOR)){
            agendamento.setCorretor((Corretor) corretor);
        }else {
            throw  new TipoUsuarioIncorretoException("corretor");
        }
        if(usuarioComum.getRole().equals(RoleEnum.USUARIO)){
            agendamento.setUsuarioComum((UsuarioComum) usuarioComum);
        }else {
            System.out.println(usuarioComum.getRole());
            throw  new TipoUsuarioIncorretoException("usuario");
        }
        agendamento.setImovel(imovelService.buscarPorId(agendamentoPostDto.idImovel()));

        agendamento.getCorretor().removerHorarioPorDatahora(agendamentoPostDto.dataHora());
        agendamento.setDataHora(agendamentoPostDto.dataHora());

        repository.save(agendamento);
    }

    public Page<Agendamento> listarAgendamentosCorretorId(Pageable pageable, Long idCorretor) {

       return repository.findByCorretorId(idCorretor, pageable);
    }


    public void atualizarStatus(Long id, StatusAgendamentoEnum status) {

        if(!this.repository.existsById(id)){
            throw new AgendamentoInexistenteException();
        }
        repository.findById(id).ifPresent(agendamento -> {
            agendamento.setStatus(status);
            repository.save(agendamento);
        });



    }
}
