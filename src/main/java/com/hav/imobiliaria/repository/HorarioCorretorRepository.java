package com.hav.imobiliaria.repository;

import com.hav.imobiliaria.model.entity.HorarioCorretor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HorarioCorretorRepository extends JpaRepository<HorarioCorretor, Long> {

}
