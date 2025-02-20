package com.hav.imobiliaria.controller.dto.usuario;

public record UsuarioPostDTO(
        String nome,
        String email,
        String senha,
        String telefone,
        String descricao,
        String role,
        String foto
) {
}
