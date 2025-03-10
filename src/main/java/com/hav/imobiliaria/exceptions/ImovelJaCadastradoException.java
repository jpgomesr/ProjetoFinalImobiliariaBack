package com.hav.imobiliaria.exceptions;

public class ImovelJaCadastradoException extends RuntimeException {
    public ImovelJaCadastradoException() {
        super("O imóvel ja foi cadastrado");
    }
}
