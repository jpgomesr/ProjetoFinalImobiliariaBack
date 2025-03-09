package com.hav.imobiliaria.controller.mapper.endereco;


import com.hav.imobiliaria.controller.dto.endereco.EnderecoPostDTO;
import com.hav.imobiliaria.model.entity.Endereco;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EnderecoPostMapper {

    Endereco toEntity(EnderecoPostDTO enderecoPostDTO);

    EnderecoPostDTO toDto(Endereco endereco);
}
