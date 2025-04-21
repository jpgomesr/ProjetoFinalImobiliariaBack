package com.hav.imobiliaria.exceptions.requisicao_padrao;

public class AgendamentoComImovelFinalizadoException extends RequisicaoPadraoException {
    public AgendamentoComImovelFinalizadoException() {
        super("Este imóvel já foi vendido ou alugado");
    }
}
