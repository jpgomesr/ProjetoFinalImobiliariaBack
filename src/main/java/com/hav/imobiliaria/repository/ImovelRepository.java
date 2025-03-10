package com.hav.imobiliaria.repository;

import com.hav.imobiliaria.model.Imovel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImovelRepository extends JpaRepository<Imovel, Long> {

    Page<Imovel> findByDeletadoFalse(Pageable pageable);

    Page<Imovel> findAll(Specification<Imovel> specs, Pageable pageableRequest);

}
