package com.hav.imobiliaria.controller.dto.imovel;

import com.hav.imobiliaria.controller.dto.endereco.EnderecoPostDTO;
import com.hav.imobiliaria.controller.dto.endereco.EnderecoPutDTO;
import com.hav.imobiliaria.model.TipoBunnerEnum;
import com.hav.imobiliaria.model.TipoFinalidadeEnum;
import jakarta.validation.constraints.*;
//a
public record ImovelPutDTO(
        @Pattern(regexp = "^[0-9]+$", message = "o id deve conter apenas números")
        @NotNull(message = "O id é obrigatório")
        Integer id,
        @Size(max = 45, message = "O título deve conter até 45 caracteres")
        @NotBlank(message = "O título é obrigatório")
        String titulo,
        @Size(max = 100, message = "O link deve conter até 100 caracteres")
        @NotBlank(message = "A imagem de capa é obrigatória")
        String imagemCapa,
        @Size(max = 45, message = "A descrição deve conter até 500 caracteres")
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
        @NotNull(message = "O CEP é obrigatório")
        @Size(max = 8, min = 8)
        String cep,
        @Pattern(regexp = "^[A-Za-zÀ-ÖØ-öø-ÿ\\s]+$", message = "O tipo da residência deve conter apenas letras e acentos")
        @NotBlank(message = "A inserção tipo de residência é obrigatório")
        String tipoResidencia,
        @NotNull(message = "Você deve permitir ou não um destaque")
        Boolean banner,
        TipoBunnerEnum tipoBanner,
        @Size(max = 45, message = "O bairro deve conter até 45 caracteres")
        @NotBlank(message = "O bairro é obrigatório")
        String bairro,
        @Size(max = 45, message = "A cidade deve conter até 45 caracteres")
        @NotBlank(message = "A cidade é obrigatória")
        @Pattern(regexp = "^[A-Za-zÀ-ÖØ-öø-ÿ\\s]+$", message = "A cidade deve conter apenas letras e espaços")
        String cidade,
        @Size(max = 45, message = "O estado deve conter até 45 caracteres")
        @NotBlank(message = "O estado é obrigatório")
        @Pattern(regexp = "^[A-Za-zÀ-ÖØ-öø-ÿ\\s]+$", message = "O estado deve conter apenas letras e espaços")
        String estado,
        @Size(max = 100, message = "Os links devem conter até 255 caracteres")
        @NotBlank(message = "A galeria de imagens é obrigatória")
        String galeriaImagens,
        @Pattern(regexp = "^[0-9]+$", message = "o IPTU deve conter apenas números")
        Double iptu,
        @Pattern(regexp = "^[0-9]+$", message = "o valor do condomínio deve conter apenas números")
        Double valorCondominio,
        @Pattern(regexp = "^[0-9]+$", message = "o id deve conter apenas números")
        @NotNull(message = "O id é obrigatório")
        Long idProprietario,
        EnderecoPutDTO enderecoPutDTO
) {
}
