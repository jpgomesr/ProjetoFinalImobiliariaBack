package com.hav.imobiliaria.controller.mapper;

import com.hav.imobiliaria.controller.dto.ProprietarioDTO;
import com.hav.imobiliaria.model.Proprietario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProprietarioMapper {

    Proprietario toEntity(ProprietarioDTO proprietarioDTO);

    ProprietarioDTO toDTO(Proprietario proprietario);
}
