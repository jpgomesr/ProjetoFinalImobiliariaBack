package com.hav.imobiliaria.exceptions;

public class EnderecoJaCadastradoException extends RuntimeException {
    public EnderecoJaCadastradoException() {
        super("Endereço já cadastrado");
    }
}
