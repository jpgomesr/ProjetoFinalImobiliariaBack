package com.hav.imobiliaria.controller.mapper.proprietario;


import com.hav.imobiliaria.controller.dto.proprietario.ProprietarioRespostaUnicaDTO;
import com.hav.imobiliaria.controller.mapper.endereco.EnderecoGetMapper;
import com.hav.imobiliaria.model.Proprietario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class ProprietarioRespostaUnicaMapper {

    @Autowired
    EnderecoGetMapper enderecoGetMapper;

    public  abstract Proprietario toEntity(ProprietarioRespostaUnicaDTO proprietarioGetDTO);

    @Mapping(target = "endereco", expression = "java(enderecoGetMapper.toDto(proprietario.getEndereco()))")
    public  abstract ProprietarioRespostaUnicaDTO toDto(Proprietario proprietario);
}
