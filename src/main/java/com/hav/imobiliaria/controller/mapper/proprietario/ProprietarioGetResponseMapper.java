package com.hav.imobiliaria.controller.mapper.proprietario;

import com.hav.imobiliaria.controller.dto.proprietario.ProprietarioGetDTO;
import com.hav.imobiliaria.controller.dto.proprietario.ProprietarioGetResponseDTO;
import com.hav.imobiliaria.model.Proprietario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class ProprietarioGetResponseMapper {

    public  abstract Proprietario toEntity(ProprietarioGetResponseDTO proprietarioGetResponseDTO);

    public  abstract ProprietarioGetResponseDTO toDto(Proprietario proprietario);
}
