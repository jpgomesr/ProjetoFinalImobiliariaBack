package com.hav.imobiliaria.service;

import com.hav.imobiliaria.controller.dto.imovel.ImovelGetDTO;
import com.hav.imobiliaria.controller.dto.imovel.ImovelPostDTO;
import com.hav.imobiliaria.controller.dto.imovel.ImovelPutDTO;
import com.hav.imobiliaria.controller.dto.usuario.UsuarioPutDTO;
import com.hav.imobiliaria.controller.mapper.imovel.ImovelGetMapper;
import com.hav.imobiliaria.controller.mapper.imovel.ImovelPostMapper;
import com.hav.imobiliaria.controller.mapper.imovel.ImovelPutMapper;
import com.hav.imobiliaria.controller.mapper.usuario.UsuarioGetMapper;
import com.hav.imobiliaria.model.Imovel;
import com.hav.imobiliaria.repository.ImovelRepository;
import com.hav.imobiliaria.validator.ImovelValidator;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ImovelService {

    private final ImovelRepository repository;
    private final ImovelValidator imovelValidator;
    private final ImovelGetMapper imovelGetMapper;
    private final ImovelPostMapper imovelPostMapper;
    private final ImovelPutMapper imovelPutMapper;

    public Page<ImovelGetDTO> buscarTodos(Pageable pageable) {
        return repository.findAll(pageable).map(imovelGetMapper::toDto);
    }


    public ImovelGetDTO buscarPorId(Long id) {
        ImovelGetDTO dto = imovelGetMapper.toDto(repository.findById(id).get());
        return dto;
    }

    public ImovelGetDTO salvar(ImovelPostDTO dto) {
        Imovel entity = imovelPostMapper.toEntity(dto);
        entity = repository.save(entity);
        return imovelGetMapper.toDto(entity);
    }

    public ImovelGetDTO atualizar(ImovelPutDTO dto, Long id){
        Imovel entity = imovelPutMapper.toEntity(dto);
        entity.setId(id);
        entity = repository.save(entity);
        return imovelGetMapper.toDto(entity);
    }

    public void removerPorId(Long id) {
        repository.deleteById(id);
    }

}
