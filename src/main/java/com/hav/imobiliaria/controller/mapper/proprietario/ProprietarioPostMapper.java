package com.hav.imobiliaria.controller.mapper.proprietario;


import com.hav.imobiliaria.controller.dto.proprietario.ProprietarioPostDTO;
import com.hav.imobiliaria.model.Proprietario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProprietarioPostMapper {

    Proprietario toEntity(ProprietarioPostDTO proprietarioPostDTO);
    ProprietarioPostDTO toDTO(Proprietario proprietario);
}
