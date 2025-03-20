package com.hav.imobiliaria.exceptions.requisicao_padrao;

public class EnderecoJaCadastradoException extends RuntimeException {
    public EnderecoJaCadastradoException() {
        super("Endereço já cadastrado");
    }
}
