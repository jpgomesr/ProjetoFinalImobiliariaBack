package com.hav.imobiliaria.controller.mapper.proprietario;


import com.hav.imobiliaria.controller.dto.imovel.ImovelPostDTO;
import com.hav.imobiliaria.controller.dto.proprietario.ProprietarioPostDTO;
import com.hav.imobiliaria.model.Imovel;
import com.hav.imobiliaria.model.Proprietario;
import com.hav.imobiliaria.repository.EnderecoRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class ProprietarioPostMapper {

    @Autowired
    protected EnderecoRepository enderecoRepository;

    @Mapping(source = "enderecoPostDTO", target = "endereco")
    public abstract Proprietario toEntity(ProprietarioPostDTO proprietario);

    public abstract ProprietarioPostDTO toDto(Proprietario proprietrio);

}
