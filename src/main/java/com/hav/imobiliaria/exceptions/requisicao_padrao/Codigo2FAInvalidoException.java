package com.hav.imobiliaria.exceptions.requisicao_padrao;

public class Codigo2FAInvalidoException extends RequisicaoPadraoException{

    public Codigo2FAInvalidoException() {
        super("O código enviado é inválido");
    }
}
