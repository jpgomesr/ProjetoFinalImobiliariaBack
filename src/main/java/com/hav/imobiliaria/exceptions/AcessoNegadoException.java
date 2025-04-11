package com.hav.imobiliaria.exceptions;

public class AcessoNegadoException extends RuntimeException {
    public AcessoNegadoException() {
        super("Acesso negado");
    }
}
