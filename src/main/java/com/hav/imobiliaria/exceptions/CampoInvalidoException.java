package com.hav.imobiliaria.exceptions;

import lombok.Getter;

@Getter
public class CampoInvalidoException extends RuntimeException{
    String campo;

    public CampoInvalidoException(String mensagem, String campo){
        super(mensagem);
        this.campo=campo;
    }
}
