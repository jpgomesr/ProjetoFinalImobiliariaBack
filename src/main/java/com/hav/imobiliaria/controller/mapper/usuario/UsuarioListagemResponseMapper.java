package com.hav.imobiliaria.controller.mapper.usuario;

import com.hav.imobiliaria.controller.dto.usuario.UsuarioListagemResponseDTO;
import com.hav.imobiliaria.model.Usuario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioListagemResponseMapper {

    UsuarioListagemResponseDTO toDto(Usuario usuario);

}
