package com.hav.imobiliaria.controller.mapper.usuario;

import com.hav.imobiliaria.controller.dto.usuario.UsuarioPostDTO;
import com.hav.imobiliaria.model.entity.*;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioPostMapper {

    UsuarioComum toUsuarioComumEntity(UsuarioPostDTO usuarioPostDTO);

    Corretor toCorretorEntity(UsuarioPostDTO usuarioPostDTO);

    Editor toEditorEntity(UsuarioPostDTO usuarioPostDTO);

    Administrador toAdministradorEntity(UsuarioPostDTO usuarioPostDTO);

    UsuarioPostDTO toDto(Usuario usuario);

}
