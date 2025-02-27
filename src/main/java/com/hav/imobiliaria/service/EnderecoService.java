package com.hav.imobiliaria.service;

import com.hav.imobiliaria.controller.dto.endereco.EnderecoGetDTO;
import com.hav.imobiliaria.controller.dto.endereco.EnderecoPostDTO;
import com.hav.imobiliaria.controller.dto.endereco.EnderecoPutDTO;
import com.hav.imobiliaria.controller.mapper.endereco.EnderecoGetMapper;
import com.hav.imobiliaria.controller.mapper.endereco.EnderecoPostMapper;
import com.hav.imobiliaria.controller.mapper.endereco.EnderecoPutMapper;
import com.hav.imobiliaria.model.Endereco;
import com.hav.imobiliaria.repository.EnderecoRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EnderecoService {

    private EnderecoRepository repository;
    private EnderecoGetMapper enderecoGetMapper;
    private EnderecoPostMapper enderecoPostMapper;
    private EnderecoPutMapper enderecoPutMapper;


    public Page<EnderecoGetDTO> buscarTodos(Pageable pageable) {
        return repository.findAll(pageable).map(enderecoGetMapper::toDto);
    }

    public EnderecoGetDTO buscarPorId(Long id) {
        EnderecoGetDTO dto = enderecoGetMapper.toDto(repository.findById(id).get());
        return dto;
    }

    public Endereco salvar(EnderecoPostDTO dto) {
        Endereco entity = enderecoPostMapper.toEntity(dto);

        entity = repository.save(entity);
        return entity;
    }

    public Endereco atualizar(EnderecoPutDTO dto, Long id) {
        Endereco entity = enderecoPutMapper.toEntity(dto);
        entity.setId(id);
        entity = repository.save(entity);
        return entity;
    }

    public void removerPorId(Long id) {
        repository.deleteById(id);
    }
}
