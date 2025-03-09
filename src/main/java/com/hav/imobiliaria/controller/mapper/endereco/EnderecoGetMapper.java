package com.hav.imobiliaria.controller.mapper.endereco;


import com.hav.imobiliaria.controller.dto.endereco.EnderecoGetDTO;
import com.hav.imobiliaria.model.entity.Endereco;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EnderecoGetMapper {

    Endereco toEntity(EnderecoGetDTO enderecoGetDTO);

    EnderecoGetDTO toDto(Endereco endereco);
}
