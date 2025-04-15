package com.hav.imobiliaria.exceptions;

public class TokenInvalidoException extends RuntimeException {
    public TokenInvalidoException() {
        super("O token enviado é inválido");
    }
}
