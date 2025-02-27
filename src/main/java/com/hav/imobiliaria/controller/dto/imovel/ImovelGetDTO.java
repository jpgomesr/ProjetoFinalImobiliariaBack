package com.hav.imobiliaria.controller.dto.imovel;

import com.hav.imobiliaria.controller.dto.endereco.EnderecoGetDTO;
import com.hav.imobiliaria.controller.dto.proprietario.ProprietarioGetDTO;
import com.hav.imobiliaria.model.TipoBunnerEnum;
import com.hav.imobiliaria.model.TipoFinalidadeEnum;

public record ImovelGetDTO(
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
        String tipoResidencia,
        Integer numeroCasaPredio,
        Integer numeroApartamento,
        Boolean banner,
        TipoBunnerEnum tipoBanner,
        String galeriaImagens,
        Double iptu,
        Double valorCondominio,
        ProprietarioGetDTO proprietarioGetDTO,
        EnderecoGetDTO enderecoGetDTO
) {
}
