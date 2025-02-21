package com.hav.imobiliaria.controller.dto.usuario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UsuarioPutDTO(
        @Size(max = 45, message = "O nome deve conter até 45 caracteres")
        @NotBlank(message = "O nome é obrigatório")
        @Pattern(regexp = "^[A-Za-zÀ-ÖØ-öø-ÿ\\s]+$", message = "O nome deve conter apenas letras e espaços")
        String nome,
        @Size(max = 45, message = "O email deve conter até 45 caracteres")
        @NotBlank(message = "O email é obrigatório")
        String email,
        @Size(max = 30, message = "A senha deve conter até 30 caracteres")
        @NotBlank(message = "A senha é obrigatória")
        String senha,
        @Pattern(regexp = "^[0-9]{11}$", message = "O telefone deve conter exatamente 11 dígitos numéricos")
        String telefone,
        @Size(max = 500, message = "A mensagem deve conter até 500 caracteres")
        String descricao,
        @NotBlank(message = "A role é obrigatória")
        String role,
        String foto
) {}