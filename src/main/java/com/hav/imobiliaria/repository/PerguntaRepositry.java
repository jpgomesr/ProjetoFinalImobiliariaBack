package com.hav.imobiliaria.repository;

import com.hav.imobiliaria.model.entity.Pergunta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PerguntaRepositry extends JpaRepository<Pergunta, Long> {
    Page<Pergunta> findAll(Specification<Pergunta> specs, Pageable pageable);
}
