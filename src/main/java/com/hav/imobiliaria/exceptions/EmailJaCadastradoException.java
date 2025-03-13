package com.hav.imobiliaria.exceptions;

public class EmailJaCadastradoException extends CampoInvalidoException {
    public EmailJaCadastradoException() {
        super("email", "E-mail já cadastrado");
    }
}
