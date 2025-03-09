package com.hav.imobiliaria.controller.dto.usuario;

public record UsuarioListagemResponseDTO(
        Long id,
        String role,
        String nome,
        String email,
        String foto,
        Boolean ativo
)
{
}
