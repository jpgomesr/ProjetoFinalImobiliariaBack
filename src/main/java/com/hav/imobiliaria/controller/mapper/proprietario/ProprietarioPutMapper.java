package com.hav.imobiliaria.controller.mapper.proprietario;


import com.hav.imobiliaria.controller.dto.proprietario.ProprietarioPutDTO;
import com.hav.imobiliaria.model.entity.Proprietario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProprietarioPutMapper {

    Proprietario toEntity(ProprietarioPutDTO proprietarioPutDTO);
    ProprietarioPutDTO toDTO(Proprietario proprietario);
}
