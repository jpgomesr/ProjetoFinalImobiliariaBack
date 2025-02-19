package com.hav.imobiliaria.controller.dto;

public record UsuarioDTO(
        Long id,
        String tipo,
        String nome,
        String telefone,
        String email,
        String descricao,
        String foto
) {}