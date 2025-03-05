package com.hav.imobiliaria.controller.mapper.imovel;

import com.hav.imobiliaria.controller.dto.endereco.EnderecoGetDTO;
import com.hav.imobiliaria.controller.dto.imovel.ImovelGetDTO;
import com.hav.imobiliaria.controller.dto.proprietario.ProprietarioGetDTO;
import com.hav.imobiliaria.controller.mapper.endereco.EnderecoGetMapper;
import com.hav.imobiliaria.controller.mapper.proprietario.ProprietarioGetMapper;
import com.hav.imobiliaria.controller.mapper.proprietario.ProprietarioGetResponseMapper;
import com.hav.imobiliaria.model.Endereco;
import com.hav.imobiliaria.model.Imovel;
import com.hav.imobiliaria.model.Proprietario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class ImovelGetMapper {

    @Autowired
    EnderecoGetMapper enderecoGetMapper;

    @Autowired
    ProprietarioGetResponseMapper proprietarioGetResponseMapper;

    public  abstract Endereco toEntity(EnderecoGetDTO enderecoGetDTO);

    @Mapping(target = "enderecoGetDTO", expression = "java(enderecoGetMapper.toDto(imovel.getEndereco()))")
    @Mapping(target = "proprietarioGetResponseDTO", expression = "java(proprietarioGetResponseMapper.toDto(imovel.getProprietario()))" +
            "")
    public  abstract ImovelGetDTO toDto(Imovel imovel);
}
