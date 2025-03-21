package com.hav.imobiliaria.controller.dto.proprietario;

import com.hav.imobiliaria.controller.dto.endereco.EnderecoPutDTO;
import jakarta.validation.constraints.*;

public record ProprietarioPutDTO (
        @Size(max = 45, message = "O nome deve conter até 45 caracteres")
        @NotBlank(message = "O nome é obrigatório")
        @Pattern(regexp = "^[A-Za-zÀ-ÖØ-öø-ÿ\\s]+$", message = "O nome deve conter apenas letras e espaços")
        String nome,
        @Pattern(regexp = "^[0-9]{11}$", message = "O telefone deve conter exatamente 11 dígitos numéricos")
        @NotBlank(message = "O telefone é obrigatório")
        String telefone,
        @Pattern(regexp = "^[0-9]{11}$", message = "O telefone deve conter exatamente 11 dígitos numéricos")
        @NotBlank(message = "O celular é obrigatório")
        String celular,
        @Pattern(regexp = "^[0-9]{11}$", message = "O CPF deve conter exatamente 11 dígitos numéricos")
        @NotBlank(message = "O CPF é obrigatório")
        String cpf,
        @Email(message = "Insira um e-mail válido")
        @NotNull(message = "O e-mail é obrigatório")
        String email,
        Boolean ativo,
        @NotNull(message = "O endereço é obrigatório")
        EnderecoPutDTO enderecoPutDTO
){
}
