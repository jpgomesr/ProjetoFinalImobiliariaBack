package com.hav.imobiliaria.controller.mapper.usuario;

import com.hav.imobiliaria.controller.dto.usuario.CorretorResponseDto;
import com.hav.imobiliaria.controller.dto.usuario.CorretorRespostaImovelDto;
import com.hav.imobiliaria.controller.dto.usuario.UsuarioListagemResponseDTO;
import com.hav.imobiliaria.controller.mapper.imovel.ImovelGetMapper;
import com.hav.imobiliaria.model.entity.Corretor;
import com.hav.imobiliaria.model.entity.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

@Mapper(componentModel = "spring")
public abstract class UsuarioListagemResponseMapper {

    @Autowired
    @Lazy
    ImovelGetMapper imovelGetMapper;

    public  abstract  UsuarioListagemResponseDTO toDto(Usuario usuario);

    public  abstract  CorretorRespostaImovelDto toCorretorRespostaImovelDto(Usuario usuario);

    @Mapping(target = "imoveis", expression = "java(corretor.getImoveis().stream().map(imovelGetMapper::toImovelListagemDto).toList())")
    public abstract  CorretorResponseDto toCorretorResponseDto(Corretor corretor);

}
