package com.hav.imobiliaria.service;

import com.hav.imobiliaria.controller.dto.endereco.EnderecoGetDTO;
import com.hav.imobiliaria.controller.dto.endereco.EnderecoPostDTO;
import com.hav.imobiliaria.controller.dto.endereco.EnderecoPutDTO;
import com.hav.imobiliaria.controller.mapper.endereco.EnderecoGetMapper;
import com.hav.imobiliaria.controller.mapper.endereco.EnderecoPostMapper;
import com.hav.imobiliaria.controller.mapper.endereco.EnderecoPutMapper;
import com.hav.imobiliaria.model.entity.Endereco;
import com.hav.imobiliaria.repository.EnderecoRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EnderecoService {


    public final EnderecoRepository enderecoRepository;


    public Set<String> buscarCidades(String estado){

        return enderecoRepository.buscarCidadesDeUmEstado(estado);
    }

    public Set<String> buscarBarrosPorCidade(String cidade){

        return enderecoRepository.buscarBairrosDeUmaCidade(cidade);
    }
    public Set<String> buscarEstados(){
        return enderecoRepository.findAll().stream().map(Endereco::getEstado).collect(Collectors.toSet());
    }

}
