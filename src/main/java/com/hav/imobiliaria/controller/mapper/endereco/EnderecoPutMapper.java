package com.hav.imobiliaria.controller.mapper.endereco;

import com.hav.imobiliaria.controller.dto.endereco.EnderecoPutDTO;
import com.hav.imobiliaria.model.Endereco;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EnderecoPutMapper {

    Endereco toEntity(EnderecoPutDTO enderecoPutDTO);

    EnderecoPutDTO toDto(Endereco endereco);
}
