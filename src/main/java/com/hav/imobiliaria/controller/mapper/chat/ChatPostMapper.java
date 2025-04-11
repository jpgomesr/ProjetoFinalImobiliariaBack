package com.hav.imobiliaria.controller.mapper.chat;

import com.hav.imobiliaria.controller.dto.chat.ChatPostDTO;
import com.hav.imobiliaria.controller.dto.usuario.UsuarioListaChatDTO;
import com.hav.imobiliaria.model.entity.Chats;
import com.hav.imobiliaria.model.entity.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ChatPostMapper {
    @Mapping(target = "usuario1", expression = "java(toUsuarioListaChatDTO(chatSalvo.getUsuarios().get(0)))")
    @Mapping(target = "usuario2", expression = "java(chatSalvo.getUsuarios().size() > 1 ? toUsuarioListaChatDTO(chatSalvo.getUsuarios().get(1)) : null)")
    ChatPostDTO toDto(Chats chatSalvo);
    
    @Mapping(source = "id", target = "id")
    @Mapping(source = "nome", target = "nome")
    @Mapping(source = "foto", target = "foto")
    UsuarioListaChatDTO toUsuarioListaChatDTO(Usuario usuario);
}
