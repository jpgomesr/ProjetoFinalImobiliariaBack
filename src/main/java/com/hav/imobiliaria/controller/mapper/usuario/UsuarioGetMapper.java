package com.hav.imobiliaria.controller.mapper.usuario;

import com.hav.imobiliaria.controller.dto.usuario.UsuarioGetDTO;
import com.hav.imobiliaria.controller.dto.usuario.UsuarioListaChatDTO;
import com.hav.imobiliaria.model.entity.Usuario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioGetMapper {

    Usuario toEntity(UsuarioGetDTO usuarioGetDTO);

    UsuarioGetDTO toDto(Usuario usuario);

    UsuarioListaChatDTO toChatDTO(Usuario usuario);

}
