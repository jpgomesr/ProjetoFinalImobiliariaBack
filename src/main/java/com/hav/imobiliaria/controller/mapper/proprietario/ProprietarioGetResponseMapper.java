package com.hav.imobiliaria.controller.mapper.proprietario;

import com.hav.imobiliaria.controller.dto.proprietario.ProprietarioGetResponseDTO;
import com.hav.imobiliaria.model.Proprietario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class ProprietarioGetResponseMapper {

    public abstract Proprietario toEntity(ProprietarioGetResponseDTO proprietarioGetResponseDTO);

    public abstract ProprietarioGetResponseDTO toDto(Proprietario proprietario);
}
