package com.hav.imobiliaria.repository;

import com.hav.imobiliaria.model.entity.Agendamento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {



    Page<Agendamento> findByCorretorId(Long id, Pageable pageable);


    @Query("SELECT c.nome, e.bairro, e.rua, e.numeroCasaPredio, a.dataHora, i.id, img.referencia " +
            "FROM Agendamento a " +
            "JOIN a.corretor c " +
            "JOIN a.imovel i " +
            "JOIN i.endereco e " +
            "JOIN i.imagens img " +
            "WHERE img.imagemCapa = true AND c.id = a.corretor.id")
    Page<Agendamento> findByUsuarioId(Long id, Pageable pageable);


}
