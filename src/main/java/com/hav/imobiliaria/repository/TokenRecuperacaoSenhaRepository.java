package com.hav.imobiliaria.repository;

import com.hav.imobiliaria.controller.dto.usuario.TrocaDeSenhaDTO;
import com.hav.imobiliaria.model.entity.TokenRecuperacaoSenha;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface TokenRecuperacaoSenhaRepository extends JpaRepository<TokenRecuperacaoSenha, Long> {


    void deleteByUsuario_Id(Long id);

    Optional<TokenRecuperacaoSenha> findByToken(String token);
}
