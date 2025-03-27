package com.hav.imobiliaria.controller.dto.chat;

import com.hav.imobiliaria.controller.dto.usuario.UsuarioListaChatDTO;
import jakarta.persistence.Column;

import java.util.List;

public record ChatGetDTO(
        Long id,
        Long idChat,
        UsuarioListaChatDTO usuario1,
        UsuarioListaChatDTO usuario2,
        List<ChatMessageDTO> messages
) {
}
