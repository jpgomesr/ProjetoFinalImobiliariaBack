package com.hav.imobiliaria.controller.mapper.imovel;

import com.hav.imobiliaria.controller.dto.endereco.EnderecoGetDTO;
import com.hav.imobiliaria.controller.dto.imovel.ImovelGetDTO;
import com.hav.imobiliaria.controller.mapper.endereco.EnderecoGetMapper;
import com.hav.imobiliaria.controller.mapper.proprietario.ProprietarioGetResponseMapper;
import com.hav.imobiliaria.controller.mapper.usuario.UsuarioListagemResponseMapper;
import com.hav.imobiliaria.model.entity.Endereco;
import com.hav.imobiliaria.model.entity.Imovel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class ImovelGetMapper {

    @Autowired
    EnderecoGetMapper enderecoGetMapper;

    @Autowired
    ProprietarioGetResponseMapper proprietarioGetResponseMapper;

    @Autowired
    UsuarioListagemResponseMapper usuarioListagemResponseMapper;

    public  abstract Endereco toEntity(EnderecoGetDTO enderecoGetDTO);

    @Mapping(target = "enderecoGetDTO", expression = "java(enderecoGetMapper.toDto(imovel.getEndereco()))")
    @Mapping(target = "proprietarioGetResponseDTO", expression = "java(proprietarioGetResponseMapper.toDto(imovel.getProprietario()))")
    @Mapping(target = "corretores", expression = "java(imovel.getCorretores().stream().map(usuarioListagemResponseMapper::toCorretorRespostaImovelDto).toList())")
    public  abstract ImovelGetDTO toDto(Imovel imovel);

   
}
