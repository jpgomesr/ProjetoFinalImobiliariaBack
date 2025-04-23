package com.hav.imobiliaria.exceptions.requisicao_padrao;

import com.hav.imobiliaria.exceptions.campo.CampoInvalidoException;

public class SenhaUtilizadaRecentementeException extends RequisicaoPadraoException {
    public SenhaUtilizadaRecentementeException() {
        super("A senha informada já foi utilizada anteriormente");
    }
}
