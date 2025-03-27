package com.hav.imobiliaria.controller.mapper.chat;

import com.hav.imobiliaria.controller.dto.chat.ChatGetDTO;
import com.hav.imobiliaria.model.entity.Chats;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ChatGetMapper {
    ChatGetDTO toDto(Chats chat);
}
