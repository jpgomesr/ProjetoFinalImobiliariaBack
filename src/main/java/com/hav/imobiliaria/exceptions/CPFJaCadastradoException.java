package com.hav.imobiliaria.exceptions;

public class CPFJaCadastradoException extends RuntimeException {
  public CPFJaCadastradoException(String message) {
    super("CPF já cadastrado");
  }
}
