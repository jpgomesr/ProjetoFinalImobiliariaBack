package com.hav.imobiliaria.controller.mapper.usuario;

import com.hav.imobiliaria.controller.dto.usuario.UsuarioPostDTO;
import com.hav.imobiliaria.controller.dto.usuario.UsuarioPutDTO;
import com.hav.imobiliaria.model.entity.*;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioPutMapper {


    UsuarioComum toUsuarioComumEntity(UsuarioPutDTO usuarioPutDTO);

    Corretor toCorretorEntity(UsuarioPutDTO usuarioPutDTO);

    Editor toEditorEntity(UsuarioPutDTO usuarioPutDTO);

    Administrador toAdministradorEntity(UsuarioPutDTO usuarioPutDTO);

    UsuarioPutDTO toDto(Usuario usuario);

}
