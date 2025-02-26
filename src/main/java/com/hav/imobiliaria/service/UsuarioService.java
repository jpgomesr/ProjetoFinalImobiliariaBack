package com.hav.imobiliaria.service;

import com.hav.imobiliaria.controller.dto.usuario.UsuarioGetDTO;
import com.hav.imobiliaria.controller.dto.usuario.UsuarioPostDTO;
import com.hav.imobiliaria.controller.dto.usuario.UsuarioPutDTO;
import com.hav.imobiliaria.controller.mapper.usuario.UsuarioGetMapper;
import com.hav.imobiliaria.controller.mapper.usuario.UsuarioPostMapper;
import com.hav.imobiliaria.controller.mapper.usuario.UsuarioPutMapper;
import com.hav.imobiliaria.model.Usuario;
import com.hav.imobiliaria.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UsuarioService {

    private final UsuarioRepository repository;
    private final S3Service s3Service;
    private final UsuarioGetMapper usuarioGetMapper;
    private final UsuarioPostMapper usuarioPostMapper;
    private final UsuarioPutMapper usuarioPutMapper;

    public Page<UsuarioGetDTO> buscarTodos(Pageable pageable) {
        return repository.findAll(pageable).map(usuarioGetMapper::toDto);

    }
    public Page<Usuario> buscarUsuarioPorNome(String nome, Pageable pageable) {
        return repository.findByNomeContaining(nome, pageable);
    }
    public UsuarioGetDTO buscarPorId(Long id) {
        UsuarioGetDTO dto = usuarioGetMapper.toDto(repository.findById(id).get());

        return dto;
    }
    public UsuarioGetDTO salvar(UsuarioPostDTO dto, MultipartFile foto) throws IOException {
        String url = null;
        if(foto != null) {
            url = s3Service.uploadArquivo(foto);
        }

        Usuario entity = usuarioPostMapper.toEntity(dto);
        entity.setFoto(url);
        entity = repository.save(entity);
        return usuarioGetMapper.toDto(entity);
    }
    public UsuarioGetDTO atualizar(UsuarioPutDTO dto, Long id) {
        Usuario entity = usuarioPutMapper.toEntity(dto);
        entity.setId(id);
        entity = repository.save(entity);
        return usuarioGetMapper.toDto(entity);
    }
    public void removerPorId(Long id) {
        Optional<Usuario> usuarioOptional = repository.findById(id);
        if(usuarioOptional.isPresent()){
            Usuario usuario = usuarioOptional.get();
            if(usuario.getFoto() != null){
                this.s3Service.excluirObjeto(usuario.getFoto());
            }
            repository.deleteById(id);

        }
    }

}
