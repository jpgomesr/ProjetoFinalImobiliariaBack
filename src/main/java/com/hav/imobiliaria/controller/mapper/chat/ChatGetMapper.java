package com.hav.imobiliaria.controller.mapper.chat;

import com.hav.imobiliaria.controller.dto.chat.ChatGetDTO;
import com.hav.imobiliaria.controller.dto.chat.ChatMessageDTO;
import com.hav.imobiliaria.controller.dto.usuario.UsuarioListaChatDTO;
import com.hav.imobiliaria.model.entity.ChatMessage;
import com.hav.imobiliaria.model.entity.Chats;
import com.hav.imobiliaria.model.entity.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ChatGetMapper {
    @Mapping(source = "idChat", target = "idChat")
    @Mapping(target = "ultimaMensagem", expression = "java(null)")
    ChatGetDTO toDto(Chats chat);
    
    @Mapping(source = "conteudo", target = "conteudo")
    @Mapping(source = "remetente", target = "remetente")
    @Mapping(source = "timeStamp", target = "timeStamp")
    @Mapping(source = "lida", target = "lida")
    ChatMessageDTO toMessageDto(ChatMessage message);
    
    @Mapping(source = "id", target = "id")
    @Mapping(source = "nome", target = "nome")
    @Mapping(source = "foto", target = "foto")
    UsuarioListaChatDTO toUsuarioDto(Usuario usuario);
}
