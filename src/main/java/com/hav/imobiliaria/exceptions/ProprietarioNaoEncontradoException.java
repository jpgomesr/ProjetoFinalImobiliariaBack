package com.hav.imobiliaria.exceptions;

public class ProprietarioNaoEncontradoException extends RuntimeException{
    public ProprietarioNaoEncontradoException(){
        super("Proprietario não encontrado");
    }
}
