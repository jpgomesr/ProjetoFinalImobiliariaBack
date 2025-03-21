package com.hav.imobiliaria.repository;

import com.hav.imobiliaria.model.entity.ImagemImovel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImagemImovelRepository extends JpaRepository<ImagemImovel,Long> {
}
