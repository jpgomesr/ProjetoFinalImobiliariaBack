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
           "LEFT JOIN FETCH c.usuario1 " +
           "LEFT JOIN FETCH c.usuario2 " +
           "LEFT JOIN FETCH c.messages " +
           "WHERE c.idChat = :idChat")
    Optional<Chats> findByIdChatWithUsersAndMessages(@Param("idChat") Long idChat);

    List<Chats> findAllByUsuario1IdOrUsuario2IdOrderByMessagesTimeStamp(Long usuario1, Long usuario2);

    boolean existsByUsuario1IdAndUsuario2IdOrUsuario1IdAndUsuario2Id(
            Long usuario1Id1, Long usuario2Id1,
            Long usuario1Id2, Long usuario2Id2
    );
}
