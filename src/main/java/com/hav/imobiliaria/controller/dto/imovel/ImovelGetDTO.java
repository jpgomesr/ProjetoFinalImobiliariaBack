package com.hav.imobiliaria.controller.dto.imovel;

import com.hav.imobiliaria.controller.dto.endereco.EnderecoGetDTO;
import com.hav.imobiliaria.controller.dto.proprietario.ProprietarioGetResponseDTO;
import com.hav.imobiliaria.controller.dto.usuario.CorretorRespostaImovelDto;
import com.hav.imobiliaria.model.entity.Corretor;
import com.hav.imobiliaria.model.enums.TipoBunnerEnum;
import com.hav.imobiliaria.model.enums.TipoFinalidadeEnum;

import java.util.List;

public record ImovelGetDTO(
        Integer id,
        String titulo,
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
        Boolean banner,
        TipoBunnerEnum tipoBanner,
        List<ImagemImovelResponseDTO> imagens,
        Double iptu,
        Double valorCondominio,
        ProprietarioGetResponseDTO proprietario,
        List<CorretorRespostaImovelDto> corretores,
        EnderecoGetDTO endereco
) {
}
