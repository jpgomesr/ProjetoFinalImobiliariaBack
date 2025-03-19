package com.hav.imobiliaria.exceptions;

public class TipoUsuarioIncorretoException extends RequisicaoPadraoException {
    public TipoUsuarioIncorretoException(String message) {
        super("O usuário informado não é um " + message);
    }
}
