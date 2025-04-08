package com.hav.imobiliaria.controller.dto.usuario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TrocaDeSenha(



        @Size(min = 8, max = 45, message = "A senha deve conter entre 8 a 45 caractéres")
        @NotBlank(message = "A senha é obrigatória")
        String senha
) {
}
