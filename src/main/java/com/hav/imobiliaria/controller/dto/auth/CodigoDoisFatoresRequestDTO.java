package com.hav.imobiliaria.controller.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CodigoDoisFatoresRequestDTO(
        @Email
        @NotBlank
        String email,
        @NotBlank
        @Size(min = 6, max = 6)
        String codigo,
        @NotBlank
        String senha
){
}
