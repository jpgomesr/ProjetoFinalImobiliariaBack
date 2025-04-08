package com.hav.imobiliaria.model.enums;

import lombok.Getter;

@Getter
public enum TipoEmailEnum {
    RESPOSTA_PERGUNTA("resposta-pergunta"),
    ATUALIZACAO_IMOVEL("atualizacao-imovel"),
    NOTIFICACAO_IMOBILIARIA("notificacao");

    private final String refHtml;

    TipoEmailEnum(String refHtml) {
        this.refHtml = refHtml;
    }
}
