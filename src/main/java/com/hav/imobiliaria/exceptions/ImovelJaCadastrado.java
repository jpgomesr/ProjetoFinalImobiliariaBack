package com.hav.imobiliaria.exceptions;

public class ImovelJaCadastrado extends RuntimeException {
    public ImovelJaCadastrado() {
        super("O imóvel ja foi cadastrado");
    }
}
