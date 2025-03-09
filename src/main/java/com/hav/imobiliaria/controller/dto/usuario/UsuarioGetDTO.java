package com.hav.imobiliaria.controller.dto.usuario;

import com.hav.imobiliaria.controller.dto.endereco.EnderecoGetDTO;
import com.hav.imobiliaria.model.enums.RoleEnum;

public record UsuarioGetDTO (
        Long id,
        RoleEnum role,
        String nome,
        String telefone,
        String email,
        String descricao,
        Boolean ativo,
        String foto){
}
