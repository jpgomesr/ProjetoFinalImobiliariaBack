package com.hav.imobiliaria.controller.dto.notificacao;

import java.time.LocalDateTime;

public record NotificacaoResponseDTO(
        Long id,
        String titulo,
        String descricao,
        LocalDateTime dataCriacao,
        Boolean lido
) {
}
