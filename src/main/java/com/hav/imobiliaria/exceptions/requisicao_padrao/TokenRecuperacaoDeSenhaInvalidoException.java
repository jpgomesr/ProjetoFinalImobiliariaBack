package com.hav.imobiliaria.exceptions.requisicao_padrao;

public class TokenRecuperacaoDeSenhaInvalidoException extends RequisicaoPadraoException {
    public TokenRecuperacaoDeSenhaInvalidoException() {
        super("O token para redefinição de senha é inválido");
    }
}
