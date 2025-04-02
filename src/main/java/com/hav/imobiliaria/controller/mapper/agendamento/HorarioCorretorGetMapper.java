package com.hav.imobiliaria.controller.mapper.agendamento;

import com.hav.imobiliaria.controller.dto.agendamento.HorarioCorretorGetDTO;
import com.hav.imobiliaria.model.entity.HorarioCorretor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface HorarioCorretorGetMapper {

    @Mapping(target = "idCorretor", expression = "java(horarioCorretor.getCorretor().getId())")
    HorarioCorretorGetDTO toDTO(HorarioCorretor horarioCorretor);


}
