package com.hav.imobiliaria.controller.dto.pergunta;

import com.hav.imobiliaria.model.entity.Usuario;
import com.hav.imobiliaria.model.enums.TipoPerguntaEnum;

import java.time.LocalDateTime;

public record PerguntaGetDTO(
        Long id,
        TipoPerguntaEnum tipoPergunta,
        String email,
        String titulo,
        String mensagem,
        Boolean perguntaRespondida,
        String resposta,
        LocalDateTime data
) {

}
