package com.hav.imobiliaria.controller.dto.agendamento;

import java.time.LocalDateTime;

public record HorarioCorretorGetDTO(
        Long id,
        LocalDateTime dataHora,
        Long idCorretor
) {
}
