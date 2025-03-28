package com.hav.imobiliaria.controller.mapper.chat;

import com.hav.imobiliaria.controller.dto.chat.ChatResponseDTO;
import com.hav.imobiliaria.controller.dto.usuario.UsuarioListaSelectResponseDTO;
import com.hav.imobiliaria.model.entity.Chats;
import com.hav.imobiliaria.model.entity.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ChatResponseMapper {
    @Mapping(source = "messages", target = "mensagens")
    ChatResponseDTO toDto(Chats chats);
    
    @Mapping(source = "id", target = "id")
    @Mapping(source = "nome", target = "nome")
    @Mapping(source = "foto", target = "foto")
    UsuarioListaSelectResponseDTO toUsuarioDto(Usuario usuario);
}
