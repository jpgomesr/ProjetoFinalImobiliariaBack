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
import com.hav.imobiliaria.model.ImagemImovel;
import com.hav.imobiliaria.model.Imovel;
import com.hav.imobiliaria.model.Proprietario;
import com.hav.imobiliaria.repository.ImagemImovelRepository;
import com.hav.imobiliaria.repository.ImovelRepository;
import com.hav.imobiliaria.validator.ImovelValidator;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Value;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

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



    public ImovelGetDTO salvar(ImovelPostDTO dto, MultipartFile imagemPrincipal, List<MultipartFile> imagens) throws IOException {
        Endereco enderecoSalvo = enderecoService.salvar(dto.enderecoPostDTO());

        ImagemImovel imagemPrincipalEntidade = salvarImagemPrincipal(imagemPrincipal);
        List<ImagemImovel> imagensImovel = salvarImagens(imagens);
        imagensImovel.add(imagemPrincipalEntidade);

        Imovel entity = imovelPostMapper.toEntity(dto);

        entity.setEndereco(enderecoSalvo);

        entity.setImagens(imagensImovel);

        entity = repository.save(entity);

        return imovelGetMapper.toDto(entity);
    }

    public Page<ImovelGetDTO> buscarTodos(Pageable pageable) {
        return repository.findByDeletadoFalse(pageable).map(imovelGetMapper::toDto);
    }

    public ImovelGetDTO buscarPorId(Long id) {
        ImovelGetDTO dto = imovelGetMapper.toDto(repository.findById(id).get());
        return dto;
    }

    public ImovelGetDTO atualizar(@Positive(message = "O id deve ser positivo") Long id,
                                  ImovelPutDTO dto, MultipartFile imagemPrincipal, List<MultipartFile> imagens) throws IOException  {



        Imovel imovelExistente = repository.findById(id).get();
        Endereco enderecoAtualizado = enderecoService.atualizar(dto.enderecoPutDTO(), imovelExistente.getEndereco().getId());
        Imovel entity = imovelPutMapper.toEntity(dto);
        entity.setId(id);
        entity.setEndereco(enderecoAtualizado);
        entity.setImagens(imovelExistente.getImagens());
        if(imagemPrincipal != null) {
            atualizarImagemPrincipalImovel(entity, imagemPrincipal);
        }
        if(imagens != null) {
            atualizarImagens(entity, imagens);
        }
        entity = repository.save(entity);

        return imovelGetMapper.toDto(entity);

    }
    public void removerPorId(Long id) {
        Imovel imovel = this.repository.findById(id).get();
        imovel.setDeletado(true);
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
                if(imagem.getImagemCapa()){
                    removerImagemPorReferencia(imagem.getReferencia());
                }
            }
            ImagemImovel imagemImovel = salvarImagemPrincipal(imagemPrincipal);
            imovel.addImagem(imagemImovel);


    }
    private void atualizarImagens(Imovel imovel, List<MultipartFile> imagens) throws IOException {


            if(imovel.getImagens().size() + imagens.size() <= 4){
                List<ImagemImovel> imagensImovel = salvarImagens(imagens);
                imagensImovel.forEach(imovel::addImagem);
            }


    }

    public void restaurarImagem(Long id) {
        Imovel imovel = this.repository.findById(id).get();

        imovel.setDeletado(false);
        imovel.setDataDelecao(null);

        this.repository.save(imovel);
    }
}
