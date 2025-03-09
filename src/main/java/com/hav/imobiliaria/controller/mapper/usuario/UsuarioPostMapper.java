package com.hav.imobiliaria.controller.mapper.usuario;

import com.hav.imobiliaria.controller.dto.usuario.UsuarioPostDTO;
import com.hav.imobiliaria.model.entity.Usuario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioPostMapper {

    Usuario toEntity(UsuarioPostDTO usuarioPostDTO);

    UsuarioPostDTO toDto(Usuario usuario);

}
