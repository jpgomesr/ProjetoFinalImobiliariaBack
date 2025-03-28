package com.hav.imobiliaria.validator;

import com.hav.imobiliaria.controller.dto.agendamento.AgendamentoPostDto;
import com.hav.imobiliaria.exceptions.requisicao_padrao.AgendamentoJaCadastradoException;
import com.hav.imobiliaria.model.entity.Agendamento;
import com.hav.imobiliaria.model.entity.UsuarioComum;
import com.hav.imobiliaria.model.enums.StatusAgendamentoEnum;
import com.hav.imobiliaria.repository.AgendamentoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AgendamentoValidator {

    private final AgendamentoRepository agendamentoRepository;

    public void validarAgendamento(AgendamentoPostDto agendamentoPostDto) {

    }
    public void verificarAgendamentoImovelUsuario(UsuarioComum usuarioComum, Agendamento agendamento) {
        if (usuarioComum.getAgendamentos().stream()
                .anyMatch(agendamento1 ->
                        agendamento1.getImovel().getId().equals(agendamento.getImovel().getId()) &&
                                !agendamento1.getStatus().equals(StatusAgendamentoEnum.CONCLUIDO)
                )) {
            throw new AgendamentoJaCadastradoException();
        }
    }


}
