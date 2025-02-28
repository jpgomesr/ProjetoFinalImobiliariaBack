package com.hav.imobiliaria.controller.dto.endereco;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record EnderecoPutDTO (
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
        String estado,
        @Size(max = 45, message = "A rua deve conter até 45 caracteres")
        @NotBlank(message = "A rua é obrigatória")
        @Pattern(regexp = "^[A-Za-zÀ-ÖØ-öø-ÿ\\s]+$", message = "A rua deve conter apenas letras e espaços")
        String rua,
        @NotNull(message = "O CEP é obrigatório")
        @Size(max = 8, min = 8, message = "O cep deve ter 8 digitos")
        String cep
){
}
