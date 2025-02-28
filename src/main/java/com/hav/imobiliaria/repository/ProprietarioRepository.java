package com.hav.imobiliaria.repository;

import com.hav.imobiliaria.model.Proprietario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProprietarioRepository extends JpaRepository<Proprietario, Long> {
    Page<Proprietario> findByNomeContaining(String nome, Pageable pageable);

    Page<Proprietario> findAll(Specification<Proprietario> specs, Pageable pageableRequest);
}
