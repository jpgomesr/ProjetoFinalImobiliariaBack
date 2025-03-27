package com.hav.imobiliaria.controller.mapper.pergunta;

import com.hav.imobiliaria.controller.dto.pergunta.PerguntaGetDTO;
import com.hav.imobiliaria.model.entity.Pergunta;
import org.mapstruct.Mapper;

import java.util.Map;

@Mapper(componentModel = "spring")
public abstract class PerguntaGetMapper {
    public abstract Pergunta toEntity(PerguntaGetDTO perguntaGetDTO);

    public abstract PerguntaGetDTO toDto(Pergunta pergunta);
}
