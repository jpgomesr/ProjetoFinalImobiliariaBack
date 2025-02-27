package com.hav.imobiliaria.controller.mapper.proprietario;


import com.hav.imobiliaria.controller.dto.proprietario.ProprietarioGetDTO;
import com.hav.imobiliaria.controller.mapper.endereco.EnderecoGetMapper;
import com.hav.imobiliaria.model.Proprietario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class ProprietarioGetMapper {

    @Autowired
    EnderecoGetMapper enderecoGetMapper;

    public  abstract Proprietario toEntity(ProprietarioGetDTO proprietarioGetDTO);

    @Mapping(target = "enderecoGetDTO", expression = "java(enderecoGetMapper.toDto(proprietario.getEndereco()))")
    public  abstract ProprietarioGetDTO toDto(Proprietario proprietario);
}
