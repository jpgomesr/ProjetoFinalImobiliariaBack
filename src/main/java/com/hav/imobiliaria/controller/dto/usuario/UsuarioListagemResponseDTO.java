package com.hav.imobiliaria.controller.dto.usuario;

import com.hav.imobiliaria.model.enums.RoleEnum;

public record UsuarioListagemResponseDTO(
        Long id,
        RoleEnum role,
        String nome,
        String email,
        String foto,
        Boolean ativo
)
{
}
