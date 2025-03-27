package com.hav.imobiliaria.controller.dto.chat;

import com.hav.imobiliaria.controller.dto.usuario.UsuarioListaChatDTO;

public record ChatGetDTO(
        Long id,
        Long idChat,
        UsuarioListaChatDTO usuario1,
        UsuarioListaChatDTO usuario2
) {
}
