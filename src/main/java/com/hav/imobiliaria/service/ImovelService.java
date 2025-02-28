package com.hav.imobiliaria.service;

import com.hav.imobiliaria.controller.dto.endereco.EnderecoPostDTO;
import com.hav.imobiliaria.controller.dto.imovel.ImovelGetDTO;
import com.hav.imobiliaria.controller.dto.imovel.ImovelPostDTO;
import com.hav.imobiliaria.controller.dto.imovel.ImovelPutDTO;
import com.hav.imobiliaria.controller.dto.usuario.UsuarioPutDTO;
import com.hav.imobiliaria.controller.mapper.imovel.ImovelGetMapper;
import com.hav.imobiliaria.controller.mapper.imovel.ImovelPostMapper;
import com.hav.imobiliaria.controller.mapper.imovel.ImovelPutMapper;
import com.hav.imobiliaria.controller.mapper.usuario.UsuarioGetMapper;
import com.hav.imobiliaria.model.Endereco;
import com.hav.imobiliaria.model.Imovel;
import com.hav.imobiliaria.model.Proprietario;
import com.hav.imobiliaria.model.TipoFinalidadeEnum;
import com.hav.imobiliaria.repository.ImovelRepository;
import com.hav.imobiliaria.repository.specs.ImovelSpecs;
import com.hav.imobiliaria.validator.ImovelValidator;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.hav.imobiliaria.repository.specs.ImovelSpecs.tipoResidenciaEqual;
import static com.hav.imobiliaria.repository.specs.ImovelSpecs.tituloLike;

@Service
@AllArgsConstructor
public class ImovelService {

    private final ImovelRepository repository;
    private final ImovelValidator imovelValidator;
    private final EnderecoService enderecoService;
    private final ImovelGetMapper imovelGetMapper;
    private final ImovelPostMapper imovelPostMapper;
    private final ImovelPutMapper imovelPutMapper;




    public ImovelGetDTO salvar(ImovelPostDTO dto) {
        Endereco enderecoSalvo = enderecoService.salvar(dto.enderecoPostDTO());
        Imovel entity = imovelPostMapper.toEntity(dto);
        entity.setEndereco(enderecoSalvo);
        entity = repository.save(entity);

        return imovelGetMapper.toDto(entity);
    }

    public Page<ImovelGetDTO> buscarTodos(Pageable pageable) {
        return repository.findAll(pageable).map(imovelGetMapper::toDto);
    }

    public ImovelGetDTO buscarPorId(Long id) {
        ImovelGetDTO dto = imovelGetMapper.toDto(repository.findById(id).get());
        return dto;
    }

    public ImovelGetDTO atualizar(ImovelPutDTO dto, Long id) {
        Imovel imovelExistente = repository.findById(id).get();
        Endereco enderecoAtualizado = enderecoService.atualizar(dto.enderecoPutDTO(), imovelExistente.getEndereco().getId());
        Imovel entity = imovelPutMapper.toEntity(dto);
        entity.setId(id);
        entity.setEndereco(enderecoAtualizado);
        entity = repository.save(entity);
        return imovelGetMapper.toDto(entity);
    }
    public void removerPorId(Long id) {
        repository.deleteById(id);
    }



    public Page<ImovelGetDTO> pesquisa(String descricao,
                                 Integer tamanho,
                                 String titulo,
                                 String tipoResidencia,
                                 Integer qtdBanheiros,
                                 Integer qtdQuartos,
                                 Integer qtdGaragens,
                                 Double precoMin,
                                 Double precoMax,
                                 TipoFinalidadeEnum finalidade,
                                 Integer pagina,
                                 Integer tamanhoPagina) {

        Specification<Imovel> specs = Specification.where((root, query, cb) -> cb.conjunction());

        if (titulo != null) {
            specs = specs.and(ImovelSpecs.tituloLike(titulo));
        }
        if (descricao != null) {
            specs = specs.and(ImovelSpecs.descricaoLike(descricao));
        }
        if (tipoResidencia != null) {
            specs = specs.and(ImovelSpecs.tipoResidenciaEqual(tipoResidencia));
        }
        if (qtdQuartos != null) {
            specs = specs.and(ImovelSpecs.qtdQuartosEqual(qtdQuartos));
        }
        if (tamanho != null) {
            specs = specs.and(ImovelSpecs.tamanhoEqual(tamanho));
        }
        if (qtdGaragens != null) {
            specs = specs.and(ImovelSpecs.qtdGaragensEqual(qtdGaragens));
        }
        if (qtdBanheiros != null) {
            specs = specs.and(ImovelSpecs.qtdBanheirosEqual(qtdBanheiros));
        }
        if (precoMin != null && precoMax != null) {
            specs = specs.and(ImovelSpecs.precoBetween(precoMin, precoMax));
        }
        if (finalidade != null) {
            specs = specs.and(ImovelSpecs.finalidadeEqual(finalidade));
        }

        Pageable pageableRequest = PageRequest.of(pagina, tamanhoPagina);

        Page<Imovel> paginaResultado = repository.findAll(specs, pageableRequest);

        return paginaResultado.map(imovelGetMapper::toDto);
    }


}
