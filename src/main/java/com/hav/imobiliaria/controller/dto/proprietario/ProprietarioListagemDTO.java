package com.hav.imobiliaria.controller.dto.proprietario;

public record ProprietarioListagemDTO(
        Long id,
        String nome,
        String telefone,
        String cpf,
        String email,
        String imagemUrl
) {
}
