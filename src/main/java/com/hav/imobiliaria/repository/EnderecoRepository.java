package com.hav.imobiliaria.repository;

import com.hav.imobiliaria.model.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
    Boolean existsByBairroAndCidadeAndEstadoAndCepAndRua(String bairro, String cidade,
                                                         String estado, String cep, String rua);
}
