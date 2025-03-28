package com.hav.imobiliaria.controller;

import com.hav.imobiliaria.controller.mapper.chat.ChatGetMapper;
import com.hav.imobiliaria.exceptions.ChatNaoEncontradoException;
import com.hav.imobiliaria.model.entity.ChatMessage;
import com.hav.imobiliaria.model.entity.Chats;
import com.hav.imobiliaria.model.entity.Usuario;
import com.hav.imobiliaria.payload.MessageRequest;
import com.hav.imobiliaria.repository.ChatsRepository;
import com.hav.imobiliaria.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Controller
@AllArgsConstructor
@CrossOrigin("*")
public class ChatController {
    private ChatsRepository repository;
    private UsuarioRepository usuarioRepository;
    private final ChatGetMapper chatGetMapper;
    private final SimpMessageSendingOperations messageTemplate;

    @MessageMapping("/sendMessage/{idChat}")
    @SendTo("/topic/chat/{idChat}")
    @Transactional
    public Map<String, Object> sendMessage(
            @DestinationVariable Long idChat,
            @RequestBody MessageRequest request
    ) {
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
        message.setLida(false); // Inicialmente, a mensagem não está lida
        message.setChat(chat);
        
        chat.getMessages().add(message);
        repository.save(chat);
        
        // Criar um mapa com os campos necessários para o frontend, incluindo o nomeRemetente
        Map<String, Object> response = new HashMap<>();
        response.put("id", message.getId());
        response.put("conteudo", message.getConteudo());
        response.put("remetente", message.getRemetente());
        response.put("timeStamp", message.getTimeStamp());
        response.put("nomeRemetente", request.getNomeRemetente());
        response.put("lida", message.getLida());
        
        // Enviar notificação global com o ID do chat atualizado
        Map<String, Object> globalNotification = new HashMap<>();
        globalNotification.put("chatId", idChat);
        globalNotification.put("timestamp", LocalDateTime.now());
        messageTemplate.convertAndSend("/topic/chat/global", globalNotification);
        
        return response;
    }

    @Notifica
}
