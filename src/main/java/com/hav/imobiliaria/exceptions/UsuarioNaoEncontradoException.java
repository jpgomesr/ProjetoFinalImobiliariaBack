package com.hav.imobiliaria.exceptions;

public class UsuarioNaoEncontradoException extends RuntimeException {
    public UsuarioNaoEncontradoException(String message) {
        super("Usuário não encontrado");
    }
}
