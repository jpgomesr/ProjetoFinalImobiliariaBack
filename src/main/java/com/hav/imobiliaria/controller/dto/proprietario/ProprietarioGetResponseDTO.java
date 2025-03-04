package com.hav.imobiliaria.controller.dto.proprietario;

import com.hav.imobiliaria.controller.dto.endereco.EnderecoGetDTO;

public record ProprietarioGetResponseDTO(
        Long id,
        String nome,
        String telefone,
        String cpf
) {
}
