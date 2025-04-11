package com.hav.imobiliaria.repository;

import com.hav.imobiliaria.model.entity.ChatMessage;
import com.hav.imobiliaria.model.entity.Chats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatMessagesRepository extends JpaRepository<ChatMessage, Long> {
    @Query("SELECT m FROM ChatMessage m WHERE m.chat.idChat = :idChat AND m.lida = false AND m.remetente <> :usuarioId")
    List<ChatMessage> findByIdChatAndLidaFalseAndRemetenteIdNot(@Param("idChat") Long idChat, @Param("usuarioId") Long usuarioId);
    
    @Query("SELECT COUNT(m) FROM ChatMessage m WHERE m.chat.idChat = :idChat AND m.lida = false AND m.remetente <> :usuarioId")
    long countByIdChatAndLidaFalseAndRemetenteIdNot(@Param("idChat") Long idChat, @Param("usuarioId") Long usuarioId);
    
    @Query("SELECT m FROM ChatMessage m WHERE m.chat.idChat = :idChat ORDER BY m.timeStamp ASC")
    List<ChatMessage> findByChatIdChat(@Param("idChat") Long idChat);
    
    @Query("SELECT m FROM ChatMessage m WHERE m.chat = :chat ORDER BY m.timeStamp DESC")
    List<ChatMessage> findTopByChatOrderByTimeStampDesc(@Param("chat") Chats chat);
    
    default Optional<ChatMessage> findFirstByChatOrderByTimeStampDesc(Chats chat) {
        List<ChatMessage> messages = findTopByChatOrderByTimeStampDesc(chat);
        return messages.isEmpty() ? Optional.empty() : Optional.of(messages.get(0));
    }
}
