package com.hav.imobiliaria.controller.dto.proprietario;

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
        @Pattern(regexp = "^[0-9]{8}$", message = "O CEP deve conter exatamente 8 dígitos numéricos")
        @NotBlank(message = "O CEP é obrigatório")
        String cep,
        @Size(max = 45, message = "A rua deve conter até 45 caracteres")
        @NotBlank(message = "A rua é obrigatória")
        String rua,
        @Size(max = 45, message = "O tipo da residência deve conter até 45 caracteres")
        @NotBlank(message = "O tipo da residência é obrigatório")
        String tipoResidencia,
        @NotNull(message = "O número é obrigatório")
        @Positive(message = "O numero deve ser positivo")
        Integer numeroCasaPredio,
        @Positive(message = "O numero do apartamento deve ser positivo")
        Integer numeroApartamento,
        @Size(max = 45, message = "O bairro deve conter até 45 caracteres")
        @NotBlank(message = "O bairro é obrigatório")
        String bairro,
        @Size(max = 45, message = "A cidade deve conter até 45 caracteres")
        @NotBlank(message = "A cidade é obrigatória")
        @Pattern(regexp = "^[A-Za-zÀ-ÖØ-öø-ÿ\\s]+$", message = "A cidade deve conter apenas letras e espaços")
        String cidade,
        @Size(max = 45, message = "O estado deve conter até 45 caracteres")
        @NotBlank(message = "O estado é obrigatório")
        @Pattern(regexp = "^[A-Za-zÀ-ÖØ-öø-ÿ\\s]+$", message = "O estado deve conter apenas letras e espaços")
        String estado
){
}
