package com.hav.imobiliaria.controller.dto.agendamento;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record AgendamentoPutDTO(
        @NotNull
        Long id,
        @NotNull(message = "Informe o id do usuário")
        Long idUsuario,
        @NotNull(message = "Informe o id do corretor")
        Long idCorretor,
        @NotNull(message = "Informe o id do imóvel")
        Long idImovel,
        @NotNull(message = "Informe a data e a hora da visita")
        LocalDateTime dataHora
) {
}
