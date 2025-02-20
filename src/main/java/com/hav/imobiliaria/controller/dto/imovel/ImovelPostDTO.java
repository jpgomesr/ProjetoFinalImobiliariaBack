package com.hav.imobiliaria.controller.dto.imovel;

import com.hav.imobiliaria.model.TipoBunnerEnum;
import com.hav.imobiliaria.model.TipoFinalidadeEnum;
import jakarta.validation.constraints.*;

public record ImovelPostDTO(
        @Size(max = 45,message = "O título deve conter até 45 caracteres")
        @NotBlank(message = "O título é obrigatório")
        String titulo,
        @Size(max = 100, message = "O link deve conter até 100 caracteres")
        @NotBlank(message = "A imagem de capa é obrigatória")
        String imagemCapa,
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
        @NotNull(message = "A finalidade não deve ser nula")
        TipoFinalidadeEnum finalidade,
        Boolean academia,
        @Pattern(regexp = "^[0-9]+$", message = "O preço do imóvel deve conter apenas números")
        @NotNull(message = "O preço não deve ser nulo")
        Double preco,
        @Pattern(regexp = "^[0-9]+$", message = "O preço promocional deve conter apenas números")
        Double precoPromocional,
        @NotNull(message = "Você deve permitir ou não um destaque")
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
) {
}
