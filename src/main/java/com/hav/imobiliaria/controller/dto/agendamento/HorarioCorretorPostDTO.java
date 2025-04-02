package com.hav.imobiliaria.controller.dto.agendamento;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

public record HorarioCorretorPostDTO(

        @NotNull
        LocalDateTime horario,
        @NotNull
        @Positive
        Long idCorretor

) {
}
