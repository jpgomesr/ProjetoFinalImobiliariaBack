package com.hav.imobiliaria.controller.dto.imovel;

import com.hav.imobiliaria.model.TipoBunnerEnum;
import com.hav.imobiliaria.model.TipoFinalidadeEnum;

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
        TipoFinalidadeEnum finalidade,
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
        Long idProprietario
) {}

