package com.hav.imobiliaria.controller.mapper.proprietario;


import com.hav.imobiliaria.controller.dto.proprietario.ProprietarioGetDTO;
import com.hav.imobiliaria.model.Proprietario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProprietarioGetMapper {

    Proprietario toEntity(ProprietarioGetDTO proprietarioGetDTO);

    ProprietarioGetDTO toDto(Proprietario proprietario);
}
