package com.hav.imobiliaria.repository;

import com.hav.imobiliaria.model.entity.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
    Boolean existsByBairroAndCidadeAndEstadoAndCepAndRua(String bairro, String cidade,
                                                         String estado, String cep, String rua);

    Set<Endereco> findDistinctByCidade(String cidade);

    Set<Endereco> findDistinctByEstado(String estado);





}
