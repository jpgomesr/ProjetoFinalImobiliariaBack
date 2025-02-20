package com.hav.imobiliaria.controller.mapper.imovel;

import com.hav.imobiliaria.controller.dto.imovel.ImovelDTO;
import com.hav.imobiliaria.model.Imovel;
import com.hav.imobiliaria.repository.ProprietarioRepository;
import com.hav.imobiliaria.service.ProprietarioService;
import lombok.AllArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class ImovelMapper {

    @Autowired
    protected ProprietarioRepository repository;



    @Mapping(target = "proprietario", expression = "java(repository.findById(imovel.idProprietario()).orElse(null))")
    public  abstract Imovel toEntity(ImovelDTO imovel);

    public  abstract ImovelDTO toDto(Imovel imovel);

}
