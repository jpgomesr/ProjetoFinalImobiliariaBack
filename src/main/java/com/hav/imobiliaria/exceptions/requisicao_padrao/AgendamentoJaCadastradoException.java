package com.hav.imobiliaria.exceptions.requisicao_padrao;

public class AgendamentoJaCadastradoException extends RequisicaoPadraoException {
    public AgendamentoJaCadastradoException() {
        super("Você já possui um agendamento para este imóvel");
    }
}
