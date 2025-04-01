package com.hav.imobiliaria.controller.dto.mensagem;

import java.time.LocalDateTime;

public record MensagemResponseDTO(
        Long id,
        String conteudo,
        String remetente,
        LocalDateTime timeStamp,
        Boolean lida
) {
}
