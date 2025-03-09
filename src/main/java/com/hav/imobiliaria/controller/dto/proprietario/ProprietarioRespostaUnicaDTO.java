package com.hav.imobiliaria.controller.dto.proprietario;

import com.hav.imobiliaria.controller.dto.endereco.EnderecoGetDTO;

public record ProprietarioRespostaUnicaDTO(
        Long id,
        String nome,
        String telefone,
        String cpf,
        String email,
        String imagemUrl,
        String celular,
        EnderecoGetDTO endereco
){
}
