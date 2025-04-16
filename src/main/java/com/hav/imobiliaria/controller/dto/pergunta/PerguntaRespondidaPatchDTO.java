package com.hav.imobiliaria.controller.dto.pergunta;

import com.hav.imobiliaria.model.enums.TipoPerguntaEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PerguntaRespondidaPatchDTO(

        @NotBlank
        Boolean perguntaRespondida,
        @NotBlank
        String resposta

) {
}
