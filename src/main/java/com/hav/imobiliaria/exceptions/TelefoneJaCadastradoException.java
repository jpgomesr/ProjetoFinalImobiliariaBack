package com.hav.imobiliaria.exceptions;

public class TelefoneJaCadastradoException extends RuntimeException {
  public TelefoneJaCadastradoException() {
    super("Telefone já cadastrado");
  }
}
