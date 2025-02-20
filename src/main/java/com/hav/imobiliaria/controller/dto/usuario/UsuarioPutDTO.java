package com.hav.imobiliaria.controller.dto.usuario;

public record UsuarioPutDTO(
        Long id,
        String role,
        String nome,
        String telefone,
        String email,
        String descricao,
        String foto
) {
}