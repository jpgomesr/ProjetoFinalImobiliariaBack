package com.hav.imobiliaria.repository;

import com.hav.imobiliaria.model.entity.TokenRecuperacaoSenha;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface TokenRecuperacaoSenhaRepository extends JpaRepository<TokenRecuperacaoSenha, Long> {


    Long deleteByUsuario_Id(Long id);

}
