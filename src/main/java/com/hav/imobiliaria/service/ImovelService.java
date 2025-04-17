package com.hav.imobiliaria.service;

import com.hav.imobiliaria.controller.dto.imovel.ImovelGetDTO;
import com.hav.imobiliaria.controller.dto.imovel.ImovelPostDTO;
import com.hav.imobiliaria.controller.dto.imovel.ImovelPutDTO;
import com.hav.imobiliaria.controller.mapper.endereco.EnderecoPostMapper;
import com.hav.imobiliaria.controller.mapper.endereco.EnderecoPutMapper;
import com.hav.imobiliaria.controller.mapper.imovel.ImovelGetMapper;
import com.hav.imobiliaria.controller.mapper.imovel.ImovelPostMapper;
import com.hav.imobiliaria.controller.mapper.imovel.ImovelPutMapper;
import com.hav.imobiliaria.exceptions.AcessoNegadoException;
import com.hav.imobiliaria.model.entity.*;
import com.hav.imobiliaria.model.enums.TipoFinalidadeEnum;
import com.hav.imobiliaria.model.enums.TipoImovelEnum;
import com.hav.imobiliaria.repository.ImagemImovelRepository;
import com.hav.imobiliaria.repository.ImovelRepository;
import com.hav.imobiliaria.repository.specs.ImovelSpecs;
import com.hav.imobiliaria.security.utils.SecurityUtils;
import com.hav.imobiliaria.validator.ImovelValidator;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ImovelService {

    private final ImovelRepository repository;
    private final ImagemImovelRepository imagemImovelRepository;
    private final ImovelValidator imovelValidator;
    private final S3Service s3Service;
    private final EnderecoService enderecoService;
    private final ImovelGetMapper imovelGetMapper;
    private final ImovelPostMapper imovelPostMapper;
    private final ImovelPutMapper imovelPutMapper;
    private EnderecoPostMapper enderecoPostMapper;
    private EnderecoPutMapper enderecoPutMapper;


    public Imovel salvar(ImovelPostDTO dto, MultipartFile imagemPrincipal, List<MultipartFile> imagens) throws IOException {
        ImagemImovel imagemPrincipalEntidade = salvarImagemPrincipal(imagemPrincipal);
        List<ImagemImovel> imagensImovel = salvarImagens(imagens);
        imagensImovel.add(imagemPrincipalEntidade);

        Imovel entity = imovelPostMapper.toEntity(dto);
        entity.setEndereco(enderecoPostMapper.toEntity(dto.endereco()));

        entity.setImagens(imagensImovel);

        return repository.save(entity);
    }


    public Imovel buscarPorId(Long id) {


       return repository.findById(id).get();

    }

    public Imovel atualizar(@Positive(message = "O id deve ser positivo") Long id,
                            ImovelPutDTO dto,
                            MultipartFile imagemPrincipal,
                            List<MultipartFile> imagens,
                            List<String> refImagensExcluidas) throws IOException {

        Imovel imovelExistente = repository.findById(id).get();
        Imovel entity = imovelPutMapper.toEntity(dto);
        entity.setId(id);
        Endereco enderecoEntity = enderecoPutMapper.toEntity(dto.endereco());
        enderecoEntity.setId(imovelExistente.getEndereco().getId());
        removerImagensImovel(refImagensExcluidas, imovelExistente);

        entity.setEndereco(enderecoEntity);
        entity.setImagens(imovelExistente.getImagens());
        entity.setAtivo(dto.ativo());
        if(!entity.getBanner()){
            entity.setTipoBanner(null);
        }
        if (imagemPrincipal != null) {
            atualizarImagemPrincipalImovel(entity, imagemPrincipal);
        }
        if (imagens != null) {
            atualizarImagens(entity, imagens);
        }
        return repository.save(entity);
    }

    public void removerPorId(Long id) {
        Imovel imovel = this.repository.findById(id).get();
        imovel.setAtivo(false);
        imovel.setDataDelecao(LocalDateTime.now());

        this.repository.save(imovel);
    }

    public void removerImagemPorIdImagem(Long idImagem) {

        ImagemImovel imagemImovel = this.imagemImovelRepository.findById(idImagem).get();
        this.s3Service.excluirObjeto(imagemImovel.getReferencia());
        this.imagemImovelRepository.deleteById(idImagem);

    }

    private void removerImagemPorReferencia(String referencia) {
        s3Service.excluirObjeto(referencia);

    }

    private void removerImagensImovel(List<String> referencia, Imovel imovel) {
        if (referencia != null && !referencia.isEmpty()) {
            for (String referenciaImovel : referencia) {
                for (int i = 0; i < imovel.getImagens().size(); i++) {
                    if (imovel.getImagens().get(i).getReferencia().equals(referenciaImovel)) {
                        imovel.removeImagem(imovel.getImagens().get(i));
                        s3Service.excluirObjeto(referencia);
                    }
                }
            }
        }


    }

    private ImagemImovel salvarImagemPrincipal(MultipartFile imagemPrincipal) throws IOException {

        String urlImagemPrincipal = s3Service.uploadArquivo(imagemPrincipal);

        ImagemImovel imagemPrincipalEntidade = new ImagemImovel();
        imagemPrincipalEntidade.setImagemCapa(true);
        imagemPrincipalEntidade.setReferencia(urlImagemPrincipal);

        return imagemImovelRepository.save(imagemPrincipalEntidade);
    }

    private List<ImagemImovel> salvarImagens(List<MultipartFile> imagens) throws IOException {
        List<ImagemImovel> imagensImovel = new ArrayList<>();
        for (MultipartFile arquivo : imagens) {
            String urlArquivo = s3Service.uploadArquivo(arquivo);
            ImagemImovel imagemEntidade = new ImagemImovel();
            imagemEntidade.setImagemCapa(false);
            imagemEntidade.setReferencia(urlArquivo);
            imagemImovelRepository.save(imagemEntidade);
            imagensImovel.add(imagemEntidade);
        }
        return imagensImovel;
    }

    private void atualizarImagemPrincipalImovel(Imovel imovel, MultipartFile imagemPrincipal) throws IOException {

        for (ImagemImovel imagem : imovel.getImagens()) {
            if (imagem.getImagemCapa()) {
                removerImagemPorReferencia(imagem.getReferencia());
            }
        }
        ImagemImovel imagemImovel = salvarImagemPrincipal(imagemPrincipal);
        imovel.addImagem(imagemImovel);


    }

    private void atualizarImagens(Imovel imovel, List<MultipartFile> imagens) throws IOException {



            List<ImagemImovel> imagensImovel = salvarImagens(imagens);
            imagensImovel.forEach(imovel::addImagem);



    }


    public void restaurarImagem(Long id) {
        Imovel imovel = this.repository.findById(id).get();

        imovel.setAtivo(true);
        imovel.setDataDelecao(null);

        this.repository.save(imovel);
    }

    public Page<Imovel> pesquisa(String descricao,
                                 Integer tamanhoMinimo,
                                 Integer tamanhoMaximo,
                                 String titulo,
                                 TipoImovelEnum tipoResidencia,
                                 Integer qtdBanheiros,
                                 Integer qtdQuartos,
                                 Integer qtdGaragens,
                                 Double precoMin,
                                 Double precoMax,
                                 TipoFinalidadeEnum finalidade,
                                 String cidade,
                                 String bairro,
                                 Boolean ativo,
                                 Boolean destaque,
                                 Boolean condicoesEspeciais,
                                 Long idUsuario,
                                 Boolean buscarArquivados,
                                 Pageable pageable) {

        Specification<Imovel> specs = Specification.where((root, query, cb) -> cb.conjunction());

        cidade = cidade.replace("-", " ");
        bairro = bairro.replace("-", " ");

        if (StringUtils.isNotBlank(titulo)) {
            specs = specs.and(ImovelSpecs.tituloLike(titulo));
        }
        if(idUsuario != null && idUsuario != 0 ){
            SecurityUtils.verificarUsuarioLogado(idUsuario);
            specs = specs.and(ImovelSpecs.buscandoFavoritos(idUsuario));
        }
        if (StringUtils.isNotBlank(descricao)) {
            specs = specs.and(ImovelSpecs.descricaoLike(descricao));
        }
        if (tipoResidencia != null) {
            specs = specs.and(ImovelSpecs.tipoResidenciaEqual(tipoResidencia));
        }
        if (qtdQuartos != null && qtdQuartos != 0) {
            if(qtdQuartos < 4){
                specs = specs.and(ImovelSpecs.qtdQuartosEqual(qtdQuartos));
            }else {
                specs = specs.and(ImovelSpecs.qtdQuartosEqualOrGratherThan(qtdQuartos));
            }

        }
        if(buscarArquivados != null && buscarArquivados){
           specs = specs.and(ImovelSpecs.buscarApenasNaoArquivados());
       }
        if(destaque != null && destaque){
            specs = specs.and(ImovelSpecs.permitirDestaqueEqual(destaque));
        }
        if (condicoesEspeciais != null && condicoesEspeciais) {
            specs = specs.and(ImovelSpecs.condicoesEspeciais(condicoesEspeciais));
        }
        if (tamanhoMinimo != null && tamanhoMinimo != 0 && tamanhoMaximo != null && tamanhoMaximo != 0) {
            specs = specs.and(ImovelSpecs.tamanhoBeetween(tamanhoMinimo, tamanhoMaximo));
        }
        else if (tamanhoMinimo != null && tamanhoMinimo != 0) {
            specs = specs.and(ImovelSpecs.tamanhoMin(tamanhoMinimo));
        }
        else if (tamanhoMaximo != null && tamanhoMaximo != 0) {
            specs = specs.and(ImovelSpecs.tamanhoMax(tamanhoMaximo));
        }

        if (qtdGaragens != null && qtdGaragens != 0) {
            if(qtdGaragens < 4){
                specs = specs.and(ImovelSpecs.qtdGaragensEqual(qtdGaragens));
            }
            else {
                specs = specs.and(ImovelSpecs.qtdGaragemEqualOrGratherThan(qtdGaragens));
            }
        }
        if (qtdBanheiros != null && qtdBanheiros != 0) {
            specs = specs.and(ImovelSpecs.qtdBanheirosEqual(qtdBanheiros));
        }
        if (precoMin != null && precoMin != 0 && precoMax != null && precoMax != 0) {
            specs = specs.and(ImovelSpecs.precoBetween(precoMin, precoMax));
        }
        else if (precoMin != null && precoMin != 0) {
            specs = specs.and(ImovelSpecs.precoMin(precoMin));
        }
        else if (precoMax != null && precoMax != 0) {
            specs = specs.and(ImovelSpecs.precoMax(precoMax));
        }
        if (finalidade != null) {
            specs = specs.and(ImovelSpecs.finalidadeEqual(finalidade));
        }
        if (StringUtils.isNotBlank(bairro)) {
            specs = specs.and(ImovelSpecs.enderecoBairroEqual(bairro));
        }
        if (StringUtils.isNotBlank(cidade)) {
            specs = specs.and(ImovelSpecs.enderecoCidadeEqual(cidade));
        }
        if (ativo != null) {
            specs = specs.and(ImovelSpecs.ativo(ativo));
        }

        return repository.findAll(specs, pageable);
    }




    public List<Long> buscarTodosIds() {
        return repository.findAll().stream().map(Imovel::getId).collect(Collectors.toList());
    }
}
