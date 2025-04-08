package com.hav.imobiliaria.controller.dto.usuario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record TrocaDeSenhaDTO(

        @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[^A-Za-z\\d]).{8,45}$",
                message = "A senha deve conter pelo menos uma letra maiúscula, uma minúscula, um número e um caractere especial.")
        @Size(min = 8, max = 45, message = "A senha deve conter entre 8 a 45 caracteres.")
        @NotBlank(message = "A senha é obrigatória.")
        String senha,
        @Size(min = 32, max=36, message = "O token deve conter entre 32 a 36 caractéres")
        @NotBlank
        String token
) {
}
