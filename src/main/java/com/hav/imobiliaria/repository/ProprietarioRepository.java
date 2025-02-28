package com.hav.imobiliaria.repository;

import com.hav.imobiliaria.model.Proprietario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProprietarioRepository extends JpaRepository<Proprietario, Long> {
    Page<Proprietario> findByNomeContaining(String nome, Pageable pageable);

    Optional<Proprietario> findByCpf(String cpf);
    Optional<Proprietario> findByTelefone(String telefone);
}
