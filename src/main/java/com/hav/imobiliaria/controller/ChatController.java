package com.hav.imobiliaria.controller;

import com.hav.imobiliaria.controller.mapper.chat.ChatGetMapper;
import com.hav.imobiliaria.exceptions.ChatNaoEncontradoException;
import com.hav.imobiliaria.model.entity.ChatMessage;
import com.hav.imobiliaria.model.entity.Chats;
import com.hav.imobiliaria.payload.MessageRequest;
import com.hav.imobiliaria.repository.ChatsRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;

@Controller
@AllArgsConstructor
@CrossOrigin("*")
public class ChatController {
    private ChatsRepository repository;
    private final ChatGetMapper chatGetMapper;

    @MessageMapping("/sendMessage/{idChat}")
    @SendTo("/topic/chat/{idChat}")
    @Transactional
    public ChatMessage sendMessage(
            @DestinationVariable Long idChat,
            @RequestBody MessageRequest request
    ) {
        System.out.println(request);
        
        Chats chat = repository.findByIdChat(request.getIdChat())
                .orElseThrow(() -> new ChatNaoEncontradoException("Chat não encontrado"));
        
        // Verifica se o remetente é participante do chat
        if (!chat.getUsuario1().getId().toString().equals(request.getRemetente()) && 
            !chat.getUsuario2().getId().toString().equals(request.getRemetente())) {
            throw new RuntimeException("Usuário não autorizado para enviar mensagens neste chat");
        }
        
        ChatMessage message = new ChatMessage();
        message.setConteudo(request.getConteudo());
        message.setRemetente(request.getRemetente());
        message.setTimeStamp(LocalDateTime.now());
        message.setChat(chat);
        
        chat.getMessages().add(message);
        repository.save(chat);
        
        return message;
    }
}
