package com.hav.imobiliaria.controller.mapper.imovel;

import com.hav.imobiliaria.controller.dto.imovel.ImovelGetDTO;
import com.hav.imobiliaria.model.Imovel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ImovelGetMapper {

    Imovel toEntity(ImovelGetDTO ImovelGetDTO);

    ImovelGetDTO toDto(Imovel imovel);

}
