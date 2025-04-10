package com.hav.imobiliaria.repository;

import com.hav.imobiliaria.model.entity.Chats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatsRepository extends JpaRepository<Chats, Long> {
    Optional<Chats> findByIdChat(Long idChat);

    @Query("SELECT DISTINCT c FROM Chats c " +
           "LEFT JOIN FETCH c.usuarios " +
           "WHERE c.idChat = :idChat")
    Optional<Chats> findByIdChatWithUsersAndMessages(@Param("idChat") Long idChat);
    
    @Query("SELECT c FROM Chats c " +
           "WHERE c.idChat = :idChat " +
           "AND :idUsuario MEMBER OF c.usuarios")
    Optional<Chats> findByIdChatAndUsuarioId(@Param("idChat") Long idChat, @Param("idUsuario") Long idUsuario);

    @Query("SELECT DISTINCT c FROM Chats c " +
           "LEFT JOIN FETCH c.usuarios u " +
           "LEFT JOIN FETCH c.messages m " +
           "WHERE :idUsuario MEMBER OF c.usuarios " +
           "ORDER BY (SELECT MAX(m2.timeStamp) FROM ChatMessage m2 WHERE m2.chat = c) DESC NULLS LAST")
    List<Chats> findAllByUsuario1IdOrUsuario2IdOrderByMessagesTimeStamp(
            @Param("idUsuario") Long idUsuario);
            
    @Query("SELECT DISTINCT c FROM Chats c " +
           "LEFT JOIN FETCH c.usuarios u " +
           "WHERE EXISTS (SELECT 1 FROM c.usuarios u2 WHERE u2.id = :idUsuario) " +
           "ORDER BY (SELECT MAX(m2.timeStamp) FROM ChatMessage m2 WHERE m2.chat = c) DESC NULLS LAST")
    List<Chats> findAllByUsuario1IdOrUsuario2IdOrderByLastMessageTime(
            @Param("idUsuario") Long idUsuario);

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM Chats c " +
           "WHERE c.idChat = :idChat " +
           "AND EXISTS (SELECT 1 FROM c.usuarios u WHERE u.id = :idUsuario)")
    boolean isUserParticipantInChat(@Param("idChat") Long idChat, @Param("idUsuario") Long idUsuario);
    
    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM Chats c " +
           "WHERE c.idChat = :idChat")
    boolean existsByIdChat(@Param("idChat") Long idChat);
    
    @Query("SELECT c FROM Chats c " +
           "LEFT JOIN FETCH c.messages " +
           "WHERE c.idChat = :idChat")
    Optional<Chats> findByIdChatWithMessagesOnly(@Param("idChat") Long idChat);
    
    @Query("SELECT DISTINCT c FROM Chats c " +
           "JOIN c.usuarios u1 " +
           "JOIN c.usuarios u2 " +
           "WHERE u1.id = :idUsuario1 AND u2.id = :idUsuario2")
    Chats findByUsuario1IdAndUsuario2Id(@Param("idUsuario1") Long idUsuario1, @Param("idUsuario2") Long idUsuario2);
    
    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM Chats c " +
           "WHERE EXISTS (SELECT 1 FROM c.usuarios u1 WHERE u1.id = :idUsuario1) " +
           "AND EXISTS (SELECT 1 FROM c.usuarios u2 WHERE u2.id = :idUsuario2)")
    boolean existsByUsuario1IdAndUsuario2IdOrUsuario1IdAndUsuario2Id(
            @Param("idUsuario1") Long idUsuario1, 
            @Param("idUsuario2") Long idUsuario2
    );

    @Query("SELECT DISTINCT c FROM Chats c " +
           "LEFT JOIN FETCH c.messages " +
           "WHERE c.idChat = :idChat")
    Optional<Chats> findByIdChatWithMessages(@Param("idChat") Long idChat);
}
