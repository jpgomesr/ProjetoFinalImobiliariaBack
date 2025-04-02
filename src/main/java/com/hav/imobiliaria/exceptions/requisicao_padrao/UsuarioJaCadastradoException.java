package com.hav.imobiliaria.exceptions.requisicao_padrao;

public class UsuarioJaCadastradoException extends RuntimeException {
    public UsuarioJaCadastradoException() {
        super("Usuário já cadastrado");
    }
}
