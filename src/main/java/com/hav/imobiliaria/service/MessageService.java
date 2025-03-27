package com.hav.imobiliaria.service;

import com.hav.imobiliaria.model.entity.ChatMessage;
import com.hav.imobiliaria.repository.ChatMessagesRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MessageService {
    private ChatMessagesRepository repository;

    @Transactional
    public void marcarMensagensComoLidas(Long chatId, Long usuarioId) {
        List<ChatMessage> mensagensNaoLidas = repository.findByIdChatAndLidaFalseAndRemetenteIdNot(chatId, usuarioId);
        for (ChatMessage mensagem : mensagensNaoLidas) {
            mensagem.setLida(true);
        }
        repository.saveAll(mensagensNaoLidas);
    }
}
