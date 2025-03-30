package com.hav.imobiliaria.validator;

import com.hav.imobiliaria.controller.dto.agendamento.AgendamentoPostDto;
import com.hav.imobiliaria.exceptions.requisicao_padrao.AgendamentoJaCadastradoException;
import com.hav.imobiliaria.exceptions.requisicao_padrao.AgendamentoProximoCadastradoException;
import com.hav.imobiliaria.model.entity.Agendamento;
import com.hav.imobiliaria.model.entity.UsuarioComum;
import com.hav.imobiliaria.model.enums.StatusAgendamentoEnum;
import com.hav.imobiliaria.repository.AgendamentoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Component
@AllArgsConstructor
public class AgendamentoValidator {

    private final AgendamentoRepository agendamentoRepository;

    public void validarAgendamento(AgendamentoPostDto agendamentoPostDto, UsuarioComum usuarioComum) {
        verificarAgendamentoImovelUsuario(usuarioComum, agendamentoPostDto);
        validarDataAgendamento(agendamentoPostDto.dataHora(), usuarioComum);
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


}
