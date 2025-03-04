package com.hav.imobiliaria.controller.dto.usuario;

import jakarta.validation.constraints.*;

public record UsuarioPutDTO(
        @Size(max = 100, message = "O nome deve conter até 100 caracteres")
        @NotBlank(message = "O nome é obrigatório")
        @Pattern(regexp = "^[A-Za-zÀ-ÖØ-öø-ÿ\\s]+$", message = "O nome deve conter apenas letras e espaços")
        String nome,
        @Size(max = 100, message = "O email deve conter até 100 caracteres")
        @NotBlank(message = "O e-mail é obrigatório")
        @Email(message = "Insira um e-mail valido")
        String email,
        String senha,
        @Pattern(regexp = "^[0-9]{11}$", message = "O telefone deve conter exatamente 11 dígitos numéricos")
        String telefone,
        @Size(max = 500, message = "A mensagem deve conter até 500 caracteres")
        String descricao,
        @NotBlank(message = "A role é obrigatória")
        String role,
        @NotNull(message = "O usuario precisa ter um status")
        Boolean ativo
) {
}
