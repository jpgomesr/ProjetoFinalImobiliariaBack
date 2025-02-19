package com.hav.imobiliaria.controller.dto;

import com.hav.imobiliaria.model.Proprietario;
import com.hav.imobiliaria.model.TipoBunnerEnum;

public record ImovelDTO(
        Integer id,
        String titulo,
        String imagemCapa,
        String descricao,
        Integer tamanho,
        Integer qtdQuartos,
        Integer qtdBanheiros,
        Integer qtdGaragens,
        String qtdChurrasqueira,
        Integer qtdPiscina,
        Boolean finalidade,
        Boolean academia,
        Double preco,
        Double precoPromocional,
        Boolean permitirDestaque,
        Boolean habilitarVisibilidade,
        String cep,
        String tipoResidencia,
        Integer numeroCasaPredio,
        Integer numeroApartamento,
        Boolean banner,
        TipoBunnerEnum tipoBanner,
        String bairro,
        String cidade,
        String estado,
        String galeriaImagens,
        Double iptu,
        Double valorCondominio,
        Proprietario proprietario
) {}

