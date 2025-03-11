package com.hav.imobiliaria.controller.dto.endereco;

import com.hav.imobiliaria.model.enums.TipoImovelEnum;

public record EnderecoGetDTO(
        Integer id,
        String bairro,
        String cidade,
        String estado,
        String rua,
        String cep,
        TipoImovelEnum tipoResidencia,
        Integer numeroCasaPredio,
        Integer  numeroApartamento)
{
}
