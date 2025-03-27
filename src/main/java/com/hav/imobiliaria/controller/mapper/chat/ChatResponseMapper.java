package com.hav.imobiliaria.controller.mapper.chat;

import com.hav.imobiliaria.controller.dto.chat.ChatResponseDTO;
import com.hav.imobiliaria.model.entity.Chats;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ChatResponseMapper {
    @Mapping(source = "messages", target = "mensagens")
    ChatResponseDTO toDto(Chats chats);
}
