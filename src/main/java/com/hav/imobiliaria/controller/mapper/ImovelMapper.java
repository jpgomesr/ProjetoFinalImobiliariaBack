package com.hav.imobiliaria.controller.mapper;

import com.hav.imobiliaria.controller.dto.ImovelDTO;
import com.hav.imobiliaria.model.Imovel;
import com.hav.imobiliaria.service.ProprietarioService;
import lombok.AllArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
@AllArgsConstructor
public abstract class ImovelMapper {

    private ProprietarioService service;


    @Mapping(target = "proprietario", expression = "java(service.findById(dto.idProprietario()).orElse(null))")
    public  abstract Imovel toEntity(ImovelDTO imovel);

    public  abstract ImovelDTO toDto(Imovel imovel);

}
