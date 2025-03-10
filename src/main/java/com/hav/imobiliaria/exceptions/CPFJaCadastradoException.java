package com.hav.imobiliaria.exceptions;

public class CPFJaCadastradoException extends CampoInvalidoException {
  public CPFJaCadastradoException() {
    super("cpf", "Já existe um proprietário cadastrado com esse CPF");
  }
}
