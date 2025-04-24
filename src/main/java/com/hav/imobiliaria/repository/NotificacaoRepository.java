package com.hav.imobiliaria.repository;

import com.hav.imobiliaria.model.entity.Notificacao;
import com.hav.imobiliaria.model.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificacaoRepository extends JpaRepository<Notificacao, Long> {
    List<Notificacao> findAllByUsuario_Id(Long usuarioId);
}
