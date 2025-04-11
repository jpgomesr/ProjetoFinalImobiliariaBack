package com.hav.imobiliaria.controller.mapper.notificacao;

import com.hav.imobiliaria.controller.dto.notificacao.NotificacaoResponseDTO;
import com.hav.imobiliaria.model.entity.Notificacao;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NotificacaoResponseMapper {
    NotificacaoResponseDTO toDTO(Notificacao notificacao);
    Notificacao toEntity(NotificacaoResponseDTO notificacaoResponseDTO);
}
