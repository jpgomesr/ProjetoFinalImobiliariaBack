package com.hav.imobiliaria.exceptions.requisicao_padrao;

public class AgendamentoInexistenteException extends RequisicaoPadraoException {
    public AgendamentoInexistenteException() {
        super("O agendamento informado não existe");
    }
}
