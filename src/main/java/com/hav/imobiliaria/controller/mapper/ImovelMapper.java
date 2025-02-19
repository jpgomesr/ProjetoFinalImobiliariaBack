package com.hav.imobiliaria.controller.mapper;

import com.hav.imobiliaria.controller.dto.ImovelDTO;
import com.hav.imobiliaria.model.Imovel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ImovelMapper {

    Imovel toEntity(ImovelDTO imovel);

    ImovelDTO toDto(Imovel imovel);

}
