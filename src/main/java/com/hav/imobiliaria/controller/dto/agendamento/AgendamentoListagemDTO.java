package com.hav.imobiliaria.controller.dto.agendamento;

import com.hav.imobiliaria.controller.dto.endereco.EnderecoListagemImovelDTO;

import java.time.LocalDateTime;

public record AgendamentoListagemDTO(
        LocalDateTime horario,
        EnderecoListagemImovelDTO endereco
) {
}
