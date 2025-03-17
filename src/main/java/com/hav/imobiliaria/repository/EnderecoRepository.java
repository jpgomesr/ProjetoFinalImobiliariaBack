package com.hav.imobiliaria.repository;

import com.hav.imobiliaria.model.entity.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
    Boolean existsByBairroAndCidadeAndEstadoAndCepAndRua(String bairro, String cidade,
                                                         String estado, String cep, String rua);

    @Query("SELECT distinct e.bairro from Endereco e join Imovel i on i.endereco.id = e.id where lower(e.cidade)  = ?1")
    Set<String> buscarBairrosDeUmaCidade(String cidade);


    @Query("SELECT  distinct e.cidade  from Endereco e join Imovel i  on i.endereco.id = e.id where lower( e.estado) = ?1")
    Set<String> buscarCidadesDeUmEstado(String estado);





}
