package com.hav.imobiliaria.controller.dto.imovel;

import com.hav.imobiliaria.controller.dto.endereco.EnderecoPostDTO;
import com.hav.imobiliaria.model.TipoBunnerEnum;
import com.hav.imobiliaria.model.TipoFinalidadeEnum;
import jakarta.validation.constraints.*;

public record ImovelPostDTO(
        @Size(max = 45,message = "O título deve conter até 45 caracteres")
        @NotBlank(message = "O título é obrigatório")
        String titulo,
        @Size(max = 45,message = "A descrição deve conter até 500 caracteres")
        @NotBlank(message = "A descrição é obrigatória")
        String descricao,
        @Pattern(regexp = "^[0-9]+$", message = "O tamanho deve conter apenas números")
        @NotNull(message = "O tamanho é obrigatório")
        Integer tamanho,
        @Pattern(regexp = "^[0-9]+$", message = "A quantidade de quartos deve conter apenas números")
        @NotNull(message = "A quantidade de quartos é obrigatória")
        Integer qtdQuartos,
        @Pattern(regexp = "^[0-9]+$", message = "A quantidade de banheiros deve conter apenas números")
        @NotNull(message = "A quantidade de banheiros é obrigatória")
        Integer qtdBanheiros,
        @Pattern(regexp = "^[0-9]+$", message = "A quantidade de garagens deve conter apenas números")
        Integer qtdGaragens,
        @Pattern(regexp = "^[0-9]+$", message = "A quantidade de churras deve conter apenas números")
        String qtdChurrasqueira,
        @Pattern(regexp = "^[0-9]+$", message = "A quantidade de piscinas deve conter apenas números")
        Integer qtdPiscina,
        TipoFinalidadeEnum finalidade,
        Boolean academia,
        @Pattern(regexp = "^[0-9]+$", message = "O preço do imóvel deve conter apenas números")
        @NotNull(message = "O preço não deve ser nulo")
        Double preco,
        @Pattern(regexp = "^[0-9]+$", message = "O preço promocional deve conter apenas números")
        Double precoPromocional,
        @NotNull(message = "Você deve permitir ou não um destaque")
        Boolean permitirDestaque,
        @NotNull(message = "Você deve habilitar ou não a visibilidade")
        Boolean habilitarVisibilidade,
        @Pattern(regexp = "^[A-Za-zÀ-ÖØ-öø-ÿ\\s]+$", message = "O tipo da residência deve conter apenas letras e acentos")
        @NotBlank(message = "A inserção tipo de residência é obrigatório")
        String tipoResidencia,
        @Positive(message = "O número deve ser positivo")
        @NotNull(message = "O número é obrigatório")
        Integer numeroCasaPredio,
        @Positive(message = "O número do apartamento deve ser positivo")
        Integer numeroApartamento,
        @NotNull(message = "Você deve permitir ou não um destaque")
        Boolean banner,
        TipoBunnerEnum tipoBanner,
        @Pattern(regexp = "^[0-9]+$", message = "o IPTU deve conter apenas números")
        Double iptu,
        @Pattern(regexp = "^[0-9]+$", message = "o valor do condomínio deve conter apenas números")
        Double valorCondominio,
        @NotNull(message = "O id é obrigatório")
        Long idProprietario,
        @NotNull(message = "O endereço é obrigatório")
        EnderecoPostDTO enderecoPostDTO
) {
}
