package com.hav.imobiliaria.service;

import com.hav.imobiliaria.controller.dto.proprietario.ProprietarioPostDTO;
import com.hav.imobiliaria.controller.dto.proprietario.ProprietarioPutDTO;
import com.hav.imobiliaria.controller.mapper.proprietario.ProprietarioRespostaUnicaMapper;
import com.hav.imobiliaria.controller.mapper.proprietario.ProprietarioPostMapper;
import com.hav.imobiliaria.controller.mapper.proprietario.ProprietarioPutMapper;
import com.hav.imobiliaria.model.Endereco;
import com.hav.imobiliaria.model.Proprietario;
import com.hav.imobiliaria.repository.ProprietarioRepository;
import com.hav.imobiliaria.repository.specs.ProprietarioSpecs;
import com.hav.imobiliaria.validator.ProprietarioValidator;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class ProprietarioService {

    private final ProprietarioRepository repository;
    private final EnderecoService enderecoService;
    private final ProprietarioPutMapper proprietarioPutMapper;
    private final ProprietarioPostMapper proprietarioPostMapper;
    private final ProprietarioValidator validator;
    private final S3Service s3Service;

    public Proprietario salvar(ProprietarioPostDTO dto, MultipartFile foto) throws IOException {
        Endereco enderecoSalvo = enderecoService.salvar(dto.enderecoPostDTO());
        Proprietario entity = proprietarioPostMapper.toEntity(dto);
        if(foto != null) {
            entity.setImagemUrl(this.s3Service.uploadArquivo(foto));
        }
        this.validator.validar(entity);
        entity.setEndereco(enderecoSalvo);
        return repository.save(entity);

    }

    public Proprietario buscarPorId(Long id) {
        return repository.findById(id).get();
    }

    public Proprietario atualizar(ProprietarioPutDTO dto, MultipartFile foto, Long id) throws IOException {
        Proprietario proprietarioExistente = repository.findById(id).get();
        Endereco enderecoAtualizado = enderecoService.atualizar(dto.enderecoPutDTO(), proprietarioExistente.getEndereco().getId());
        Proprietario entity = proprietarioPutMapper.toEntity(dto);
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
        entity.setEndereco(enderecoAtualizado);
        entity.setDeletado(false);
        return repository.save(entity);

    }

    public void removerPorId(Long id) {
        Proprietario proprietario = this.repository.findById(id).get();

        proprietario.setDeletado(true);
        proprietario.setDataDelecao(LocalDateTime.now());

        this.repository.save(proprietario);

    }


    public void restaurarUsuario(Long id) {
        Proprietario proprietario = this.repository.findById(id).get();
        proprietario.setDeletado(false);
        proprietario.setDataDelecao(null);

        this.repository.save(proprietario);
    }
    public Page<Proprietario> pesquisa(String nome, String cpf, String email, Integer pagina,Integer tamanhoPagina) {

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
        specs = specs.and(ProprietarioSpecs.deletadoFalse());

        Pageable pageableRequest = PageRequest.of(pagina, tamanhoPagina);

        return repository.findAll(specs, pageableRequest);
    }
}
