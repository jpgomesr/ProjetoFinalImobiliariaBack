package com.hav.imobiliaria.exceptions.requisicao_padrao;

public class AgendamentoProximoCadastradoException extends RequisicaoPadraoException {
    public AgendamentoProximoCadastradoException() {
        super("Já existe um agendamento proximo a este horario cadastrado.");
    }
}
