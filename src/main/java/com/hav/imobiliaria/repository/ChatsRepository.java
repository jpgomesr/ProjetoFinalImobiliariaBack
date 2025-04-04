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

    @Query("SELECT c FROM Chats c " +
           "LEFT JOIN FETCH c.usuario1 u1 " +
           "LEFT JOIN FETCH c.usuario2 u2 " +
           "LEFT JOIN FETCH c.messages " +
           "WHERE c.idChat = :idChat")
    Optional<Chats> findByIdChatWithUsersAndMessages(@Param("idChat") Long idChat);
    
    @Query("SELECT c FROM Chats c " +
           "LEFT JOIN FETCH c.usuario1 u1 " +
           "LEFT JOIN FETCH c.usuario2 u2 " +
           "WHERE c.idChat = :idChat " +
           "AND (u1.id = :idUsuario OR u2.id = :idUsuario)")
    Optional<Chats> findByIdChatAndUsuarioId(@Param("idChat") Long idChat, @Param("idUsuario") Long idUsuario);

    @Query("SELECT DISTINCT c FROM Chats c " +
           "LEFT JOIN FETCH c.usuario1 u1 " +
           "LEFT JOIN FETCH c.usuario2 u2 " +
           "LEFT JOIN FETCH c.messages m " +
           "WHERE c.usuario1.id = :idUsuario OR c.usuario2.id = :idUsuario " +
           "ORDER BY (SELECT MAX(m2.timeStamp) FROM ChatMessage m2 WHERE m2.chat = c) DESC NULLS LAST")
    List<Chats> findAllByUsuario1IdOrUsuario2IdOrderByMessagesTimeStamp(
            @Param("idUsuario") Long idUsuario1, 
            @Param("idUsuario") Long idUsuario2);

    boolean existsByUsuario1IdAndUsuario2IdOrUsuario1IdAndUsuario2Id(
            Long usuario1Id1, Long usuario2Id1,
            Long usuario1Id2, Long usuario2Id2
    );

    Chats findByUsuario1IdAndUsuario2Id(Long usuario1Id, Long usuario2Id);
}
