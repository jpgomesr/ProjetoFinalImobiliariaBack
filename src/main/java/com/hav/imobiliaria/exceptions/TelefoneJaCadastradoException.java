package com.hav.imobiliaria.exceptions;

public class TelefoneJaCadastradoException extends RuntimeException {
  public TelefoneJaCadastradoException(String message) {
    super("Telefone já cadastrado");
  }
}
