package com.hav.imobiliaria.controller.dto.usuario;

import com.hav.imobiliaria.model.enums.RoleEnum;

public record CorretorRespostaImovelDto (
        Long id,
        String nome,
        String telefone,
        String email,
        String descricao,
        String foto
){
}
