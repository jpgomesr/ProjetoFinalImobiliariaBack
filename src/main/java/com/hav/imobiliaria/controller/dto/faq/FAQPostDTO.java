package com.hav.imobiliaria.controller.dto.faq;

import com.hav.imobiliaria.model.enums.TipoPerguntaEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record FAQPostDTO(
        @NotNull
        TipoPerguntaEnum tipoPergunta,
        @NotBlank
        @Email
        String email,
        @NotBlank
        String telefone,
        @NotBlank
        String nome,
        @NotBlank
        String mensagem
) {
}
