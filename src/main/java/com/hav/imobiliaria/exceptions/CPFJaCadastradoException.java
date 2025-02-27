package com.hav.imobiliaria.exceptions;

public class CPFJaCadastradoException extends RuntimeException {
  public CPFJaCadastradoException() {
    super("CPF já cadastrado");
  }
}
