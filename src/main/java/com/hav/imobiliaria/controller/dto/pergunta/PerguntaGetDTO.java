package com.hav.imobiliaria.controller.dto.pergunta;

import com.hav.imobiliaria.model.enums.TipoPerguntaEnum;

public record PerguntaGetDTO(
        Long id,
        TipoPerguntaEnum tipoPergunta,
        String email,
        String titulo,
        String mensagem
) {

}
