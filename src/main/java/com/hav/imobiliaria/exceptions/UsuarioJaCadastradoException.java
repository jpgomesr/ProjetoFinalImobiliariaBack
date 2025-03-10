package com.hav.imobiliaria.exceptions;

public class UsuarioJaCadastradoException extends RuntimeException {
    public UsuarioJaCadastradoException() {
        super("Usuário já cadastrado");
    }
}
