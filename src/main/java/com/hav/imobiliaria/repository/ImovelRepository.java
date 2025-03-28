package com.hav.imobiliaria.repository;

import com.hav.imobiliaria.model.entity.Corretor;
import com.hav.imobiliaria.model.entity.Imovel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImovelRepository extends JpaRepository<Imovel, Long> {


    Page<Imovel> findAll(Specification<Imovel> specs, Pageable pageableRequest);

}
