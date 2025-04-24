package com.hav.imobiliaria.exceptions;

public class NenhumUsernamePossivelEncontrado extends RuntimeException {
    public NenhumUsernamePossivelEncontrado() {
        super("Após diversas tentativas, nenhum username encontrado!");
    }
}
