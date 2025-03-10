package com.hav.imobiliaria.controller.dto.usuario;

import com.hav.imobiliaria.controller.dto.imovel.ImovelListagemDTO;
import com.hav.imobiliaria.model.enums.RoleEnum;

import java.util.List;

public record CorretorResponseDto (
        Long id,
        RoleEnum role,
        String nome,
        String telefone,
        String email,
        String descricao,
        Boolean ativo,
        String foto,
        List<ImovelListagemDTO> imoveis
){
}
