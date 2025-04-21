package com.hav.imobiliaria.controller.dto.imovel;

import com.hav.imobiliaria.controller.dto.endereco.EnderecoPutDTO;
import com.hav.imobiliaria.model.entity.Corretor;
import com.hav.imobiliaria.model.enums.TipoBunnerEnum;
import com.hav.imobiliaria.model.enums.TipoFinalidadeEnum;
import jakarta.validation.constraints.*;

import java.util.List;

//a
public record ImovelPutDTO(
        @Size(max = 45, message = "O título deve conter até 45 caracteres")
        @NotBlank(message = "O título é obrigatório")
        String titulo,
        @Size(max = 500, message = "A descrição deve conter até 500 caracteres")
        @NotBlank(message = "A descrição é obrigatória")
        String descricao,
        @NotNull(message = "O tamanho é obrigatório")
        @Positive(message = "O tamanho deve ser positivo")
        Integer tamanho,
        @NotNull(message = "A quantidade de quartos é obrigatória")
        @PositiveOrZero(message = "A quantidade de quartos não deve ser positiva")
        Integer qtdQuartos,
        @PositiveOrZero(message = "A quantidade de banheiros não deve ser negativa")
        @NotNull(message = "A quantidade de banheiros é obrigatória")
        Integer qtdBanheiros,
        @PositiveOrZero(message = "A quantidade de garagens não deve ser negativa")
        Integer qtdGaragens,
        @PositiveOrZero(message = "A quantidade de churrasqueiras não deve ser negativa")
        String qtdChurrasqueira,
        @PositiveOrZero(message = "A quantidade de piscinas não pode ser negativa")
        Integer qtdPiscina,
        TipoFinalidadeEnum finalidade,
        Boolean academia,
        @PositiveOrZero(message = "O preço não deve ser negativo")
        @NotNull(message = "O preço não deve ser nulo")
        Double preco,
        @PositiveOrZero(message = "O preço promocional não deve ser negativo")
        Double precoPromocional,
        @NotNull(message = "Você deve permitir ou não um destaque")
        Boolean permitirDestaque,
        @NotNull(message = "Você deve habilitar ou não a visibilidade")
        Boolean ativo,
        @NotNull(message = "Você deve habilitar ou não a visibilidade")
        Boolean visibilidade,
        @NotNull(message = "Você deve permitir ou não um destaque")
        Boolean banner,
        TipoBunnerEnum tipoBanner,
        @PositiveOrZero(message = "O valor do iptu não deve ser negativo")
        Double iptu,
        @PositiveOrZero(message = "O valor do condominio não deve ser negativo")
        Double valorCondominio,
        @NotNull(message = "O id é obrigatório")
        @Positive(message = "O id deve ser positivo")
        Long idProprietario,
        @NotNull
        List<Corretor> corretores,
        @NotNull
        EnderecoPutDTO endereco
) {
}
