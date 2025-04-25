package com.hav.imobiliaria.controller;

import com.hav.imobiliaria.controller.mapper.chat.ChatGetMapper;
import com.hav.imobiliaria.exceptions.ChatNaoEncontradoException;
import com.hav.imobiliaria.model.entity.ChatMessage;
import com.hav.imobiliaria.model.entity.Chats;
import com.hav.imobiliaria.model.entity.Usuario;
import com.hav.imobiliaria.payload.MessageRequest;
import com.hav.imobiliaria.repository.ChatsRepository;
import com.hav.imobiliaria.repository.UsuarioRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Chat", description = "Operações relacionadas ao chat em tempo real")
public class ChatController {
    private ChatsRepository repository;
    private UsuarioRepository usuarioRepository;
    private final ChatGetMapper chatGetMapper;
    private final SimpMessageSendingOperations messageTemplate;

    @MessageMapping("/sendMessage/{idChat}")
    @SendTo("/topic/chat/{idChat}")
    @Transactional
    @Operation(summary = "Enviar mensagem", description = "Envia uma nova mensagem para um chat específico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Mensagem enviada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Chat não encontrado", content = @Content),
        @ApiResponse(responseCode = "403", description = "Usuário não autorizado para este chat", content = @Content)
    })
    public Map<String, Object> sendMessage(
            @Parameter(description = "ID do chat", required = true) 
            @DestinationVariable Long idChat,
            @Parameter(description = "Dados da mensagem", required = true) 
            @RequestBody MessageRequest request
    ) {
        // Verificar se o chat existe
        if (!repository.existsByIdChat(request.getIdChat())) {
            throw new ChatNaoEncontradoException("Chat não encontrado");
        }
        
        // Verificar se o usuário é participante do chat usando uma consulta otimizada
        boolean isParticipant = repository.isUserParticipantInChat(
                request.getIdChat(), 
                Long.parseLong(request.getRemetente())
        );
        
        if (!isParticipant) {
            throw new RuntimeException("Usuário não autorizado para enviar mensagens neste chat");
        }
        
        // Buscar o chat com suas mensagens
        Chats chat = repository.findByIdChatWithMessagesOnly(request.getIdChat())
                .orElseThrow(() -> new ChatNaoEncontradoException("Chat não encontrado"));
        
        // Criar a mensagem usando o construtor que já inicializa os campos
        ChatMessage message = new ChatMessage(
            request.getConteudo(),
            request.getRemetente(),
            chat
        );
        
        // Adicionar a mensagem ao chat e salvar
        chat.getMessages().add(message);
        repository.save(chat);
        
        // Criar um mapa com os campos necessários para o frontend
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
}
