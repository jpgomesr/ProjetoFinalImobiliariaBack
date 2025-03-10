package com.hav.imobiliaria.controller.mapper.proprietario;

import com.hav.imobiliaria.controller.dto.proprietario.ProprietarioListagemDTO;
import com.hav.imobiliaria.model.Proprietario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProprietarioListagemMapper {

    ProprietarioListagemDTO toDTO(Proprietario proprietario);

}
