package com.hav.imobiliaria.service;

import com.hav.imobiliaria.controller.dto.proprietario.ProprietarioGetDTO;
import com.hav.imobiliaria.controller.dto.proprietario.ProprietarioPostDTO;
import com.hav.imobiliaria.controller.dto.proprietario.ProprietarioPutDTO;
import com.hav.imobiliaria.controller.dto.usuario.UsuarioGetDTO;
import com.hav.imobiliaria.controller.dto.usuario.UsuarioPostDTO;
import com.hav.imobiliaria.controller.dto.usuario.UsuarioPutDTO;
import com.hav.imobiliaria.controller.mapper.proprietario.ProprietarioGetMapper;
import com.hav.imobiliaria.controller.mapper.proprietario.ProprietarioPostMapper;
import com.hav.imobiliaria.controller.mapper.proprietario.ProprietarioPutMapper;
import com.hav.imobiliaria.model.Proprietario;
import com.hav.imobiliaria.model.Usuario;
import com.hav.imobiliaria.repository.ProprietarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProprietarioService {

    private final ProprietarioRepository repository;
    private final ProprietarioPutMapper proprietarioPutMapper;
    private final ProprietarioPostMapper proprietarioPostMapper;
    private final ProprietarioGetMapper proprietarioGetMapper;

    public Page<ProprietarioGetDTO> buscarTodos(Pageable pageable) {
        return repository.findAll(pageable).map(proprietarioGetMapper::toDto);
    }
    public Page<Proprietario> buscarUsuarioPorNome(String nome, Pageable pageable) {
        return repository.findByNomeContaining(nome, pageable);
    }

    public ProprietarioGetDTO salvar(ProprietarioPostDTO dto) {
        Proprietario entity = proprietarioPostMapper.toEntity(dto);
        entity = repository.save(entity);
        return proprietarioGetMapper.toDto(entity);
    }

    public ProprietarioGetDTO buscarPorId(Long id) {
        ProprietarioGetDTO dto = proprietarioGetMapper.toDto(repository.findById(id).get());

        return dto;
    }

    public ProprietarioGetDTO atualizar(ProprietarioPutDTO dto, Long id) {
        Proprietario entity = proprietarioPutMapper.toEntity(dto);
        entity.setId(id);
        entity = repository.save(entity);
        return proprietarioGetMapper.toDto(entity);
    }

    public void removerPorId(Long id) {
        repository.deleteById(id);
    }



}
