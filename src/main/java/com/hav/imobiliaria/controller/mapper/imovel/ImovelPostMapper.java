package com.hav.imobiliaria.controller.mapper.imovel;

import com.hav.imobiliaria.controller.dto.imovel.ImovelPostDTO;
import com.hav.imobiliaria.model.Imovel;
import com.hav.imobiliaria.repository.ProprietarioRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class ImovelPostMapper {

    @Autowired
    protected ProprietarioRepository repository;
    @Mapping(target = "proprietario", expression = "java(repository.findById(imovel.idProprietario()).orElse(null))")
    public abstract Imovel toEntity(ImovelPostDTO imovel);

    public abstract ImovelPostDTO toDto(Imovel imovel);

}
