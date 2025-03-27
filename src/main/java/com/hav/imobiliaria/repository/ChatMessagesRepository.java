package com.hav.imobiliaria.repository;

import com.hav.imobiliaria.model.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessagesRepository extends JpaRepository<ChatMessage, Long> {
    @Query("SELECT m FROM ChatMessage m WHERE m.chat.idChat = :idChat AND m.lida = false AND m.remetente != :usuarioId")
    List<ChatMessage> findByIdChatAndLidaFalseAndRemetenteIdNot(@Param("idChat") Long idChat, @Param("usuarioId") Long usuarioId);
}
