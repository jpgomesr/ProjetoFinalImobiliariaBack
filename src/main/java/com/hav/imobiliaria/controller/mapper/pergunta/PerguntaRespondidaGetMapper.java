package com.hav.imobiliaria.controller.mapper.pergunta;

import com.hav.imobiliaria.controller.dto.pergunta.PerguntaRespondidaGetDTO;
import com.hav.imobiliaria.model.entity.Pergunta;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class PerguntaRespondidaGetMapper {
    public abstract Pergunta toEntity(PerguntaRespondidaGetDTO perguntaRespondidaGetDTO);

    public abstract PerguntaRespondidaGetDTO toDto(Pergunta pergunta);
}
