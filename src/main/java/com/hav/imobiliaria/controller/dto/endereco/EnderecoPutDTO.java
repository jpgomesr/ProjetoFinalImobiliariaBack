package com.hav.imobiliaria.controller.dto.endereco;

import com.hav.imobiliaria.model.TipoImovelEnum;
import jakarta.validation.constraints.*;

public record EnderecoPutDTO (
        @Size(max = 150, message = "O bairro deve conter até 150 caracteres")
        @NotBlank(message = "O bairro é obrigatório")
        String bairro,
        @Size(max = 150, message = "A cidade deve conter até 150 caracteres")
        @NotBlank(message = "A cidade é obrigatória")
        String cidade,
        @Size(max = 150, message = "O estado deve conter até 150 caracteres")
        @NotBlank(message = "O estado é obrigatório")
        String estado,
        @Size(max = 150, message = "A rua deve conter até 150 caracteres")
        @NotBlank(message = "A rua é obrigatória")
        String rua,
        @NotNull(message = "O CEP é obrigatório")
        @Pattern(regexp = "^[0-9]{8}$", message = "O CEP deve conter exatamente 8 dígitos numéricos")
        String cep,
        TipoImovelEnum tipoResidencia,
        @NotNull(message = "O numero não pode ser nulo")
        @Positive(message = "O numero nao pode ser negativo")
        Integer numeroCasaPredio,
        @Positive(message = "O numero do apartamento nao pode ser negativo")
        Integer  numeroApartamento
){
}
