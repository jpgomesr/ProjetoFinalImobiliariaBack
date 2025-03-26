package com.hav.imobiliaria.repository;

import com.hav.imobiliaria.model.entity.Chats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChatsRepository extends JpaRepository<Chats, Long> {
    Optional<Chats> findByIdChat(Long idChat);
    
    boolean existsByUsuario1IdAndUsuario2IdOrUsuario1IdAndUsuario2Id(
            Long usuario1Id1, Long usuario2Id1,
            Long usuario1Id2, Long usuario2Id2
    );
}
