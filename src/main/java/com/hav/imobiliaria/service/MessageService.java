package com.hav.imobiliaria.service;

import com.hav.imobiliaria.model.entity.ChatMessage;
import com.hav.imobiliaria.repository.ChatMessagesRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

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
    
    public boolean temMensagensNaoLidas(Long chatId, Long usuarioId) {
        return repository.countByIdChatAndLidaFalseAndRemetenteIdNot(chatId, usuarioId) > 0;
    }
    
    public Optional<ChatMessage> getUltimaMensagem(List<ChatMessage> mensagens) {
        if (mensagens == null || mensagens.isEmpty()) {
            return Optional.empty();
        }
        
        return mensagens.stream()
                .sorted(Comparator.comparing(ChatMessage::getTimeStamp).reversed())
                .findFirst();
    }
}
