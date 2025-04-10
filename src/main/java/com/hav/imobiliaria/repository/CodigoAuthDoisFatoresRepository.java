package com.hav.imobiliaria.repository;

import com.hav.imobiliaria.model.entity.CodigoAuthDoisFatores;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CodigoAuthDoisFatoresRepository  extends JpaRepository<CodigoAuthDoisFatores, Long> {


    Optional<CodigoAuthDoisFatores> findByCodigoAndEmail(String codigo, String email);

}
