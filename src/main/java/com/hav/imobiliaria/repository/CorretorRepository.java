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
            "JOIN h.corretor c " + // Join com a entidade Corretor
            "JOIN c.imoveis i " +  // Join com a entidade Imovel
            "WHERE i.id = :idImovel " + // Filtra pelo ID do imóvel
            "AND FUNCTION('MONTH', h.dataHora) = :mes " + // Filtra pelo mês
            "AND FUNCTION('DAY', h.dataHora) = :dia") // Filtra pelo dia
    List<HorarioCorretor> findHorariosByImovelAndData(
            @Param("idImovel") Long idImovel,
            @Param("mes") int mes,
            @Param("dia") int dia);
}
