package com.hav.imobiliaria.controller.dto.chat;

import com.hav.imobiliaria.controller.dto.mensagem.MensagemResponseDTO;
import com.hav.imobiliaria.controller.dto.usuario.UsuarioListaSelectResponseDTO;

import java.util.List;

public record ChatResponseDTO(
        Long id,
        Long idChat,
        UsuarioListaSelectResponseDTO usuario1,
        UsuarioListaSelectResponseDTO usuario2,
        List<MensagemResponseDTO> mensagens
) {
}
