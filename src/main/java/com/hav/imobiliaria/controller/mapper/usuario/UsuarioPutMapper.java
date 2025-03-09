package com.hav.imobiliaria.controller.mapper.usuario;

import com.hav.imobiliaria.controller.dto.usuario.UsuarioPutDTO;
import com.hav.imobiliaria.model.entity.Usuario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioPutMapper {


    Usuario toEntity(UsuarioPutDTO usuarioPutDTO);

    UsuarioPutDTO toDto(Usuario usuario);

}
