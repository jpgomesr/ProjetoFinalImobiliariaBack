package com.hav.imobiliaria.controller.mapper.imovel;

import com.hav.imobiliaria.controller.dto.imovel.ImovelPutDTO;
import com.hav.imobiliaria.model.Imovel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ImovelPutMapper {
    Imovel toEntity(ImovelPutDTO imovelPutDTO);

    ImovelPutDTO toDto(Imovel imovel);
}
