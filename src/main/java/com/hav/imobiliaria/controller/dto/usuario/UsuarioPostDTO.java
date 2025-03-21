package com.hav.imobiliaria.controller.dto.usuario;

import com.hav.imobiliaria.model.enums.RoleEnum;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.*;
import org.springframework.web.multipart.MultipartFile;

public record UsuarioPostDTO(
        @Size(max = 100, message = "O nome deve conter até 100 caracteres")
        @NotBlank(message = "O nome é obrigatório")
        @Pattern(regexp = "^[A-Za-zÀ-ÖØ-öø-ÿ\\s]+$", message = "O nome deve conter apenas letras e espaços")
        String nome,
        @Size(max = 100, message = "O email deve conter até 100 caracteres")
        @NotBlank(message = "O e-mail é obrigatório")
        @Email(message = "Insira um e-mail valido")
        String email,
        @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[^A-Za-z\\d]).{8,45}$",
                message = "A senha deve conter pelo menos uma letra maiúscula, uma minúscula, um número e um caractere especial.")
        @Size(min = 8, max = 45, message = "A senha deve conter entre 8 a 45 caracteres.")
        @NotBlank(message = "A senha é obrigatória.")
        String senha,
        @Nullable
        @Pattern(regexp = "^[0-9]{11}$", message = "O telefone deve conter exatamente 11 dígitos numéricos")
        String telefone,
        @Size(max = 500, message = "A mensagem deve conter até 500 caracteres")
        String descricao,
        @NotNull(message = "A role é obrigatória")
        RoleEnum role,
        Boolean ativo
) {
}
