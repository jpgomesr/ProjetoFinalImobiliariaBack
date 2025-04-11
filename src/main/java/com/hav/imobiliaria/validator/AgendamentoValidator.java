package com.hav.imobiliaria.validator;

import com.hav.imobiliaria.controller.dto.agendamento.AgendamentoPostDto;
import com.hav.imobiliaria.controller.dto.agendamento.AgendamentoPutDTO;
import com.hav.imobiliaria.exceptions.requisicao_padrao.AgendamentoJaCadastradoException;
import com.hav.imobiliaria.exceptions.requisicao_padrao.AgendamentoProximoCadastradoException;
import com.hav.imobiliaria.exceptions.requisicao_padrao.TipoUsuarioIncorretoException;
import com.hav.imobiliaria.model.entity.Agendamento;
import com.hav.imobiliaria.model.entity.Corretor;
import com.hav.imobiliaria.model.entity.Usuario;
import com.hav.imobiliaria.model.entity.UsuarioComum;
import com.hav.imobiliaria.model.enums.RoleEnum;
import com.hav.imobiliaria.model.enums.StatusAgendamentoEnum;
import com.hav.imobiliaria.repository.AgendamentoRepository;
import com.hav.imobiliaria.service.UsuarioService;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Component
@AllArgsConstructor
public class AgendamentoValidator {

    private final AgendamentoRepository agendamentoRepository;
    private final UsuarioService usuarioService;

    public void validarCriacaoAgendamento(AgendamentoPostDto agendamentoPostDto, UsuarioComum usuarioComum) {
        verificarAgendamentoImovelUsuario(usuarioComum, agendamentoPostDto);
        validarDataAgendamento(agendamentoPostDto.dataHora(), usuarioComum);
    }
    public void validarAtualizacaoAgendamento(AgendamentoPutDTO agendamentoPutDTO, UsuarioComum usuarioComum) {
        validarDataAgendamento(agendamentoPutDTO.dataHora(), usuarioComum);
    }


    private void verificarAgendamentoImovelUsuario(UsuarioComum usuarioComum, AgendamentoPostDto agendamento) {
        if (usuarioComum.getAgendamentos().stream()
                .anyMatch(agendamento1 ->
                        agendamento1.getImovel().getId().equals(agendamento.idImovel()) && verificaAgendamentoStatusFinalizado(agendamento1)

                )) {
            throw new AgendamentoJaCadastradoException();
        }
    }
    private void validarDataAgendamento(LocalDateTime dataAgendamento, UsuarioComum usuarioComum) {

        if(
                usuarioComum.getAgendamentos().stream().anyMatch(agendamento ->
                        ChronoUnit.HOURS.between(dataAgendamento, agendamento.getDataHora()) < 4
                                &&  ChronoUnit.HOURS.between(dataAgendamento, agendamento.getDataHora()) > 4
                                && verificaAgendamentoStatusFinalizado(agendamento))
        ){
            throw new AgendamentoProximoCadastradoException();
        }

    }

    private boolean verificaAgendamentoStatusFinalizado(Agendamento agendamento) {
        return !agendamento.getStatus().equals(StatusAgendamentoEnum.CONCLUIDO) &&
                !agendamento.getStatus().equals(StatusAgendamentoEnum.CANCELADO);
    }


    public void validarUsuarios(Agendamento agendamento,
                                       @NotNull(message = "Informe o id do usuário") Long idUsuario,
                                       @NotNull(message = "Informe o id do corretor") Long idCorretor) {


        Usuario corretor = usuarioService.buscarPorId(idCorretor);
        Usuario usuarioComum = usuarioService.buscarPorId(idUsuario);

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

    }
}
