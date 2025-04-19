package com.hav.imobiliaria.controller.dto.auth;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginGoogleRequestDTO
        (
                @Email
                String email,
                @NotBlank
                String nome,
                String foto

        ){
}
