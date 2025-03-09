package com.hav.imobiliaria.controller.mapper.imovel;

import com.hav.imobiliaria.controller.dto.imovel.ImovelPutDTO;
import com.hav.imobiliaria.model.entity.Imovel;
import com.hav.imobiliaria.repository.ProprietarioRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class ImovelPutMapper {

    @Autowired
    public ProprietarioRepository proprietarioRepository;

    @Mapping(target = "proprietario",expression = "java(proprietarioRepository.findById(imovelPutDTO.idProprietario()).get())")
   public abstract Imovel toEntity(ImovelPutDTO imovelPutDTO);

   public abstract ImovelPutDTO toDto(Imovel imovel);
}
