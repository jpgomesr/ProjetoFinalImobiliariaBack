package com.hav.imobiliaria.controller;

import com.hav.imobiliaria.model.entity.ChatMessage;
import com.hav.imobiliaria.model.enums.TipoMensagem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.core.AbstractDestinationResolvingMessagingTemplate;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {

    @Autowired
    private SimpMessageSendingOperations messageTemplate;

    @MessageMapping("/chat.sendMessage.{chatId}")
    @SendTo("/topic/chat.{chatId}")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage, @DestinationVariable String chatId) {
        return chatMessage;
    }

    @MessageMapping("/chat.addUser.{chatId}")
    @SendTo("/topic/chat.{chatId}")
    public ChatMessage addUser(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor,
                               @DestinationVariable String chatId) {
        headerAccessor.getSessionAttributes().put("username", chatMessage.getRemetente());
        return chatMessage;
    }

    @PostMapping("/test/sendMessage")
    public void sendTestMessage(@RequestParam String chatId, @RequestParam String message) {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setConteudo(message);
        chatMessage.setRemetente("UsuárioTeste");
        chatMessage.setTipoMensagem(TipoMensagem.MENSAGEM);
        messageTemplate.convertAndSend("/topic/chat." + chatId, chatMessage);
        System.out.println("Mensagem enviada para /topic/chat.1\n" + message);
    }
}
