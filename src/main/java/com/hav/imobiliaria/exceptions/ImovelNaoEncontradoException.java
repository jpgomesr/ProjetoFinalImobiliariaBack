package com.hav.imobiliaria.exceptions;

public class ImovelNaoEncontradoException extends RuntimeException{
    public ImovelNaoEncontradoException(){
        super("Imóvel não encontrado");
    }
}
