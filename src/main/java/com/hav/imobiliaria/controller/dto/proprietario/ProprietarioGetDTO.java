package com.hav.imobiliaria.controller.dto.proprietario;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;

public record ProprietarioGetDTO (
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
        String estado,
        Long idEndereco
){
}
