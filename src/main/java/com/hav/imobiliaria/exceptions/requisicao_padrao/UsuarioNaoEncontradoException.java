package com.hav.imobiliaria.exceptions.requisicao_padrao;

public class UsuarioNaoEncontradoException extends RequisicaoPadraoException {
    public UsuarioNaoEncontradoException() {
        super("Usuário não encontrado");
    }
}
