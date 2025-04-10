package com.hav.imobiliaria.controller.mapper.pergunta;

import com.hav.imobiliaria.controller.dto.pergunta.PerguntaRespondidaGetDTO;
import com.hav.imobiliaria.controller.dto.pergunta.PerguntaRespondidaPatchDTO;
import com.hav.imobiliaria.model.entity.Pergunta;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class PerguntaPatchMapper {
    public abstract Pergunta toEntity(PerguntaRespondidaPatchDTO perguntaRespondidaPatchDTO);

    public abstract PerguntaRespondidaPatchDTO toDto(Pergunta pergunta);

    public abstract PerguntaRespondidaGetDTO toGetDto(Pergunta pergunta);
}
