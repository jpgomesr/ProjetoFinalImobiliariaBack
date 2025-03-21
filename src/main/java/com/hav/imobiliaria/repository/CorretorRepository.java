package com.hav.imobiliaria.repository;

import com.hav.imobiliaria.model.entity.Corretor;
import com.hav.imobiliaria.model.entity.HorarioCorretor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CorretorRepository extends JpaRepository<Corretor, Long> {

    @Query("SELECT h FROM HorarioCorretor h " +
            "JOIN h.corretor c " +
            "JOIN c.imoveis i " +
            "WHERE i.id = :idImovel " +
            "AND (:mes IS NULL OR FUNCTION('MONTH', h.dataHora) = :mes) " +
            "AND (:dia IS NULL OR FUNCTION('DAY', h.dataHora) = :dia) "+
            "ORDER BY h.dataHora asc")

    List<HorarioCorretor> findHorariosByImovelAndData(
            @Param("idImovel") Long idImovel,
            @Param("mes") Integer mes,
            @Param("dia") Integer dia);
}
