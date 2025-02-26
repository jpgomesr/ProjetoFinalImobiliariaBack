package com.hav.imobiliaria.exceptions;

public class ImovelNaoEncontradoException extends RuntimeException{
    public ImovelNaoEncontradoException(String mensagem){
        super(mensagem);
    }
}
