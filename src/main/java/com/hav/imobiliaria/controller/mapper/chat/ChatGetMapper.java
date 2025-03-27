package com.hav.imobiliaria.controller.mapper.chat;

import com.hav.imobiliaria.controller.dto.chat.ChatGetDTO;
import com.hav.imobiliaria.controller.dto.chat.ChatMessageDTO;
import com.hav.imobiliaria.model.entity.Chats;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ChatGetMapper {
    @Mapping(source = "idChat", target = "idChat")
    @Mapping(source = "messages", target = "messages")
    ChatGetDTO toDto(Chats chat);
}
