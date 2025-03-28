package com.hav.imobiliaria.controller.dto.chat;

import java.time.LocalDateTime;

public record ChatMessageDTO(
    Long id,
    String conteudo,
    LocalDateTime timeStamp,
    String remetente,
    Boolean lida
) {

}