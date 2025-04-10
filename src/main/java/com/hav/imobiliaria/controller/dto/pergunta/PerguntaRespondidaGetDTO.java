package com.hav.imobiliaria.controller.dto.pergunta;

import com.hav.imobiliaria.model.entity.Usuario;
import com.hav.imobiliaria.model.enums.TipoPerguntaEnum;

import java.time.LocalDateTime;

public record PerguntaRespondidaGetDTO(
        Long id,
        TipoPerguntaEnum tipoPergunta,
        String email,
        String titulo,
        String mensagem,
        String perguntaRespondida,
        LocalDateTime data,
        String Resposta,
        Usuario idUsuario
) {
}
