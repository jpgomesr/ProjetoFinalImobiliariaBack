package com.hav.imobiliaria.controller.mapper.usuario;

import com.hav.imobiliaria.controller.dto.usuario.CorretorRespostaImovelDto;
import com.hav.imobiliaria.controller.dto.usuario.UsuarioListagemResponseDTO;
import com.hav.imobiliaria.model.entity.Usuario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioListagemResponseMapper {

    UsuarioListagemResponseDTO toDto(Usuario usuario);

    CorretorRespostaImovelDto toCorretorRespostaImovelDto(Usuario usuario);

}
