package com.hav.imobiliaria.controller.mapper.usuario;

import com.hav.imobiliaria.controller.dto.usuario.ApresentacaoCorretorDTO;
import com.hav.imobiliaria.model.entity.Usuario;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ApresentacaoCorretorGetMapper {

    ApresentacaoCorretorDTO toDTO(Usuario usuario);

    List<ApresentacaoCorretorDTO> toDTO(List<Usuario> usuarios);
}
