package com.hav.imobiliaria.controller.dto.usuario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UsuarioPostDTO(
        @Size(max = 45, message = "O nome deve conter até 45 caracteres")
        @NotBlank(message = "O nome é obrigatório")
        @Pattern(regexp = "^[A-Za-zÀ-ÖØ-öø-ÿ\\s]+$", message = "O nome deve conter apenas letras e espaços")
        String nome,
        @Size(max = 45, message = "O email deve conter até 45 caracteres")
        @NotBlank(message = "O email é obrigatório")
        String email,
        String senha,
        String telefone,
        String descricao,
        String role,
        String foto
) {
}
