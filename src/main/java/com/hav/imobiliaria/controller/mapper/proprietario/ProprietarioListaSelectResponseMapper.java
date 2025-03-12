package com.hav.imobiliaria.controller.mapper.proprietario;

import com.hav.imobiliaria.controller.dto.proprietario.ProprietarioListaSelectResponseDTO;
import com.hav.imobiliaria.model.entity.Proprietario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProprietarioListaSelectResponseMapper {


    ProprietarioListaSelectResponseDTO toDto(Proprietario proprietario);

}
