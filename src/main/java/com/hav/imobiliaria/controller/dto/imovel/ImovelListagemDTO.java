package com.hav.imobiliaria.controller.dto.imovel;

import com.hav.imobiliaria.controller.dto.endereco.EnderecoGetDTO;
import com.hav.imobiliaria.controller.dto.endereco.EnderecoListagemImovelDTO;
import com.hav.imobiliaria.controller.dto.proprietario.ProprietarioGetResponseDTO;
import com.hav.imobiliaria.controller.dto.usuario.CorretorRespostaImovelDto;
import com.hav.imobiliaria.model.enums.TipoBunnerEnum;
import com.hav.imobiliaria.model.enums.TipoFinalidadeEnum;

import java.util.List;

public record ImovelListagemDTO(

        Integer id,
        String titulo,
        String descricao,
        Integer tamanho,
        Integer qtdQuartos,
        Integer qtdBanheiros,
        Integer qtdGaragens,
        TipoFinalidadeEnum finalidade,
        Double preco,
        Double precoPromocional,
        Boolean permitirDestaque,
        Boolean ativo,
        Boolean banner,
        TipoBunnerEnum tipoBanner,
        List<ImagemImovelResponseDTO> imagens,
        EnderecoListagemImovelDTO endereco

) {
}
