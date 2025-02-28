package com.hav.imobiliaria.controller.dto.endereco;

public record EnderecoGetDTO(
        Integer id,
        String bairro,
        String cidade,
        String estado,
        String rua,
        String cep,
        Integer numeroCasaPredio,
        Integer  numeroApartamento)
{
}
