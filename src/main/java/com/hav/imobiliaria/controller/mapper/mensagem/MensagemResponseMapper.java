package com.hav.imobiliaria.controller.mapper.mensagem;

import com.hav.imobiliaria.controller.dto.mensagem.MensagemResponseDTO;
import com.hav.imobiliaria.model.entity.ChatMessage;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MensagemResponseMapper {
    MensagemResponseDTO toDto(ChatMessage chatMessage);
}
