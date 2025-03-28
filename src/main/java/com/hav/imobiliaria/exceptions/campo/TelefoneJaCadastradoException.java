package com.hav.imobiliaria.exceptions.campo;

public class TelefoneJaCadastradoException extends CampoInvalidoException {
  public TelefoneJaCadastradoException() {
    super("telefone", "Este telefone já esta cadastrado no nosso sistema");
  }
}
