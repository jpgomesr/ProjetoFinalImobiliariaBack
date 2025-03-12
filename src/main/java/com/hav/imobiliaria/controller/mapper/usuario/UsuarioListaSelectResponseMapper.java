package com.hav.imobiliaria.controller.mapper.usuario;

import com.hav.imobiliaria.controller.dto.usuario.UsuarioListaSelectResponseDTO;
import com.hav.imobiliaria.model.entity.Usuario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioListaSelectResponseMapper {

    UsuarioListaSelectResponseDTO toDto(Usuario usuario);

}
