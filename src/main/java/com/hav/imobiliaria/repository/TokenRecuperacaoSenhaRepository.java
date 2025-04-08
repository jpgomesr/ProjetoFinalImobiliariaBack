package com.hav.imobiliaria.repository;

import com.hav.imobiliaria.model.entity.TokenRecuperacaoSenha;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRecuperacaoSenhaRepository extends JpaRepository<TokenRecuperacaoSenha, Long> {
}
