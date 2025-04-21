package com.hav.imobiliaria.controller.mapper.pergunta;

import com.hav.imobiliaria.controller.dto.pergunta.PerguntaPostDTO;
import com.hav.imobiliaria.model.entity.Pergunta;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class PerguntaPostMapper {

    public abstract Pergunta toEntity(PerguntaPostDTO perguntaPostDTO);

    public abstract PerguntaPostDTO toDto(Pergunta pergunta);
}
