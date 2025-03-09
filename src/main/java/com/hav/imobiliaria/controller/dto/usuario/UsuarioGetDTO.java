package com.hav.imobiliaria.controller.dto.usuario;

import com.hav.imobiliaria.controller.dto.endereco.EnderecoGetDTO;

public record UsuarioGetDTO (
        Long id,
        String role,
        String nome,
        String telefone,
        String email,
        String descricao,
        Boolean ativo,
        String foto){
}
