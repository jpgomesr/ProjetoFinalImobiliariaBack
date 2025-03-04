package com.hav.imobiliaria.service;

import com.hav.imobiliaria.controller.dto.endereco.EnderecoGetDTO;
import com.hav.imobiliaria.controller.dto.endereco.EnderecoPostDTO;
import com.hav.imobiliaria.controller.dto.proprietario.ProprietarioGetDTO;
import com.hav.imobiliaria.controller.dto.proprietario.ProprietarioPostDTO;
import com.hav.imobiliaria.controller.dto.proprietario.ProprietarioPutDTO;
import com.hav.imobiliaria.controller.dto.usuario.UsuarioGetDTO;
import com.hav.imobiliaria.controller.dto.usuario.UsuarioPostDTO;
import com.hav.imobiliaria.controller.dto.usuario.UsuarioPutDTO;
import com.hav.imobiliaria.controller.mapper.endereco.EnderecoGetMapper;
import com.hav.imobiliaria.controller.mapper.proprietario.ProprietarioGetMapper;
import com.hav.imobiliaria.controller.mapper.proprietario.ProprietarioPostMapper;
import com.hav.imobiliaria.controller.mapper.proprietario.ProprietarioPutMapper;
import com.hav.imobiliaria.model.Endereco;
import com.hav.imobiliaria.model.Imovel;
import com.hav.imobiliaria.model.Proprietario;
import com.hav.imobiliaria.model.Usuario;
import com.hav.imobiliaria.repository.ProprietarioRepository;
import com.hav.imobiliaria.repository.specs.ProprietarioSpecs;
import com.hav.imobiliaria.validator.ImovelValidator;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class ProprietarioService {

    private final ProprietarioRepository repository;
    private final EnderecoService enderecoService;
    private final ProprietarioPutMapper proprietarioPutMapper;
    private final ProprietarioPostMapper proprietarioPostMapper;
    private final ProprietarioGetMapper proprietarioGetMapper;
    private final EnderecoGetMapper enderecoGetMapper;

    public Page<ProprietarioGetDTO> buscarTodos(Pageable pageable) {
        return repository.findAll(pageable).map(proprietarioGetMapper::toDto);
    }
    public Page<Proprietario> buscarUsuarioPorNome(String nome, Pageable pageable) {
        return repository.findByNomeContaining(nome, pageable);
    }

    public ProprietarioGetDTO salvar(ProprietarioPostDTO dto) {

        Endereco enderecoSalvo = enderecoService.salvar(dto.enderecoPostDTO());
        Proprietario entity = proprietarioPostMapper.toEntity(dto);
        entity.setEndereco(enderecoSalvo);
        entity = repository.save(entity);

        return proprietarioGetMapper.toDto(entity);
    }

    public ProprietarioGetDTO buscarPorId(Long id) {
        ProprietarioGetDTO dto = proprietarioGetMapper.toDto(repository.findById(id).get());

        return dto;
    }

    public ProprietarioGetDTO atualizar(ProprietarioPutDTO dto, Long id) {
        Proprietario proprietarioExistente = repository.findById(id).get();
        Endereco enderecoAtualizado = enderecoService.atualizar(dto.enderecoPutDTO(), proprietarioExistente.getEndereco().getId());
        Proprietario entity = proprietarioPutMapper.toEntity(dto);
        entity.setId(id);
        entity.setEndereco(enderecoAtualizado);
        entity.setDeletado(false);
        entity = repository.save(entity);
        return proprietarioGetMapper.toDto(entity);
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
    public Page<ProprietarioGetDTO> pesquisa(String nome, String cpf, String email, Integer pagina,Integer tamanhoPagina) {

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

        Pageable pageableRequest = PageRequest.of(pagina, tamanhoPagina);

        Page<Proprietario>paginaResultado = repository.findAll(specs, pageableRequest);

        return paginaResultado.map(proprietarioGetMapper::toDto);
    }
}
