package com.hav.imobiliaria.controller.dto.faq;

import com.hav.imobiliaria.model.enums.TipoPerguntaEnum;

public record FAQGetDTO(
        Long id,
        TipoPerguntaEnum tipoPergunta,
        String email,
        String telefone,
        String nome,
        String mensagem
) {

}
