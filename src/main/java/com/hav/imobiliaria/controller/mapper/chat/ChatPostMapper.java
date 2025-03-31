package com.hav.imobiliaria.controller.mapper.chat;

import com.hav.imobiliaria.controller.dto.chat.ChatPostDTO;
import com.hav.imobiliaria.model.entity.Chats;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ChatPostMapper {
    ChatPostDTO toDto(Chats chatSalvo);
}
