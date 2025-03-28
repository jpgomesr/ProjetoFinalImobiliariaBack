package com.hav.imobiliaria.exceptions.campo;

public class CPFJaCadastradoException extends CampoInvalidoException {
  public CPFJaCadastradoException() {
    super("cpf", "Já existe um proprietário cadastrado com esse CPF");
  }
}
