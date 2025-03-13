package com.hav.imobiliaria.service;

import com.hav.imobiliaria.controller.dto.proprietario.ProprietarioPostDTO;
import com.hav.imobiliaria.controller.dto.proprietario.ProprietarioPutDTO;
import com.hav.imobiliaria.controller.mapper.endereco.EnderecoGetMapper;
import com.hav.imobiliaria.controller.mapper.endereco.EnderecoPostMapper;
import com.hav.imobiliaria.controller.mapper.endereco.EnderecoPutMapper;
import com.hav.imobiliaria.controller.mapper.proprietario.ProprietarioPostMapper;
import com.hav.imobiliaria.controller.mapper.proprietario.ProprietarioPutMapper;
import com.hav.imobiliaria.model.entity.Endereco;
import com.hav.imobiliaria.model.entity.Proprietario;
import com.hav.imobiliaria.repository.ProprietarioRepository;
import com.hav.imobiliaria.repository.specs.ProprietarioSpecs;
import com.hav.imobiliaria.validator.ProprietarioValidator;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class ProprietarioService {

    private final ProprietarioRepository repository;
    private final ProprietarioPutMapper proprietarioPutMapper;
    private final ProprietarioPostMapper proprietarioPostMapper;
    private final ProprietarioValidator validator;
    private final S3Service s3Service;
    private EnderecoPostMapper enderecoPostMapper;
    private EnderecoPutMapper enderecoPutMapper;

    public Proprietario salvar(ProprietarioPostDTO dto, MultipartFile foto) throws IOException {
        Proprietario entity = proprietarioPostMapper.toEntity(dto);
        entity.setEndereco(enderecoPostMapper.toEntity(dto.enderecoPostDTO()));
        if(foto != null) {
            entity.setImagemUrl(this.s3Service.uploadArquivo(foto));
        }
        this.validator.validar(entity);
        return repository.save(entity);

    }

    public Proprietario buscarPorId(Long id) {
        return repository.findById(id).get();
    }

    public Proprietario atualizar(ProprietarioPutDTO dto, MultipartFile foto, Long id) throws IOException {
        Proprietario proprietarioExistente = repository.findById(id).get();
        Proprietario entity = proprietarioPutMapper.toEntity(dto);
        Endereco enderecoEntidade = enderecoPutMapper.toEntity(dto.enderecoPutDTO());
        enderecoEntidade.setId(proprietarioExistente.getEndereco().getId());
        entity.setEndereco(enderecoEntidade);
        entity.setId(id);
        this.validator.validar(entity);
        if(foto != null) {
            if(entity.getImagemUrl() != null) {
                this.s3Service.excluirObjeto(entity.getImagemUrl());
            }
            entity.setImagemUrl(this.s3Service.uploadArquivo(foto));
        }
        else {
            entity.setImagemUrl(proprietarioExistente.getImagemUrl());
        }
        return repository.save(entity);

    }

    public void removerPorId(Long id) {
        Proprietario proprietario = this.repository.findById(id).get();

        proprietario.setAtivo(false);
        proprietario.setDataDelecao(LocalDateTime.now());

        this.repository.save(proprietario);

    }


    public void restaurarUsuario(Long id) {
        Proprietario proprietario = this.repository.findById(id).get();
        proprietario.setAtivo(true);
        proprietario.setDataDelecao(null);

        this.repository.save(proprietario);
    }
    public Page<Proprietario> pesquisa(String nome, String cpf, String email, Boolean ativo, Pageable pageable) {

        Specification<Proprietario> specs = Specification.where((root, query, cb) -> cb.conjunction());

        if (nome != null && !nome.isEmpty()) {
            specs = specs.and(ProprietarioSpecs.nomeLike(nome));
        }
        if (cpf != null && !cpf.isEmpty()) {
            specs = specs.and(ProprietarioSpecs.cpfLike(cpf));
        }
        if (email != null && !email.isEmpty()) {
            specs = specs.and(ProprietarioSpecs.emailLike(email));
        }
        if (ativo != null) {
            specs = specs.and(ProprietarioSpecs.ativo(ativo));
        }

        return repository.findAll(specs, pageable);
    }
    public List<Proprietario> buscarTodos(){
        return repository.findAll();
    }
}
