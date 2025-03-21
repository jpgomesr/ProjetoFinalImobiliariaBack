package com.hav.imobiliaria.validator;

import com.hav.imobiliaria.controller.dto.agendamento.AgendamentoPostDto;
import com.hav.imobiliaria.model.entity.Agendamento;
import com.hav.imobiliaria.repository.AgendamentoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AgendamentoValidator {

    private final AgendamentoRepository agendamentoRepository;

    public void validarAgendamento(AgendamentoPostDto agendamentoPostDto) {

    }

}
