package com.hav.imobiliaria.controller.dto;

public record ProprietarioDTO(
        Long id,
        String nome,
        String telefone,
        String cpf,
        String cep,
        String rua,
        String tipoResidencia,
        Integer numeroCasaPredio,
        Integer numeroApartamento,
        String bairro,
        String cidade,
        String estado
) {
}
