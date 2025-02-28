package com.hav.imobiliaria.controller.dto.proprietario;

import com.hav.imobiliaria.controller.dto.endereco.EnderecoPostDTO;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.br.CPF;

public record ProprietarioPostDTO (
        @Size(max = 45, message = "O nome deve conter até 45 caracteres")
        @NotBlank(message = "O nome é obrigatório")
        @Pattern(regexp = "^[A-Za-zÀ-ÖØ-öø-ÿ\\s]+$", message = "O nome deve conter apenas letras e espaços")
        String nome,
        @Pattern(regexp = "^[0-9]{11}$", message = "O telefone deve conter exatamente 11 dígitos numéricos")
        @NotBlank(message = "O telefone é obrigatório")
        String telefone,
        @CPF
        String cpf,
        @Email
        String email,
        @NotNull(message = "O endereço é obrigatório")
        EnderecoPostDTO enderecoPostDTO
){
}
