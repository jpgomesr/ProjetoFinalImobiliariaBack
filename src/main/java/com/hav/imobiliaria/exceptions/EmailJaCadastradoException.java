package com.hav.imobiliaria.exceptions;

public class EmailJaCadastradoException extends CampoInvalidoException {
    public EmailJaCadastradoException() {
        super("email", "Já existe um usuário com este e-mail");
    }
}
