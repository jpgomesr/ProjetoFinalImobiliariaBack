package com.hav.imobiliaria.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hav.imobiliaria.controller.dto.imovel.ImovelGetDTO;
import com.hav.imobiliaria.controller.dto.imovel.ImovelListagemDTO;
import com.hav.imobiliaria.controller.dto.imovel.ImovelPostDTO;
import com.hav.imobiliaria.controller.dto.imovel.ImovelPutDTO;
import com.hav.imobiliaria.controller.dto.usuario.CorretorResponseDto;
import com.hav.imobiliaria.controller.mapper.imovel.ImovelGetMapper;
import com.hav.imobiliaria.model.entity.Corretor;
import com.hav.imobiliaria.model.entity.HorarioCorretor;
import com.hav.imobiliaria.model.entity.Imovel;
import com.hav.imobiliaria.model.enums.TipoFinalidadeEnum;
import com.hav.imobiliaria.model.enums.TipoImovelEnum;
import com.hav.imobiliaria.service.ImovelService;
import com.hav.imobiliaria.validator.DtoValidator;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("imoveis")
@AllArgsConstructor
public class ImovelController implements GenericController {
    private final ImovelService service;
    private final DtoValidator dtoValidator;
    private final ImovelGetMapper imovelGetMapper;

    @GetMapping
    public ResponseEntity<Page<ImovelListagemDTO>> listarImoveis(
            @RequestParam(value = "descricao", required = false) String descricao,
            @RequestParam(value = "tamanhoMinimo", required = false) Integer tamanhoMinimo,
            @RequestParam(value = "tamanhoMaximo", required = false) Integer tamanhoMaximo,
            @RequestParam(value = "titulo", required = false) String titulo,
            @RequestParam(value = "tipoResidencia", required = false) TipoImovelEnum tipoResidencia,
            @RequestParam(value = "qtdQuartos", required = false) Integer qtdQuartos,
            @RequestParam(value = "qtdBanheiros", required = false) Integer qtdBanheiros,
            @RequestParam(value = "qtdGaragens", required = false) Integer qtdGaragens,
            @RequestParam(value = "precoMinimo", required = false) Double precoMin,
            @RequestParam(value = "precoMaximo", required = false) Double precoMax,
            @RequestParam(value = "finalidade", required = false) TipoFinalidadeEnum finalidade,
            @RequestParam(value = "cidade", required = false) String cidade,
            @RequestParam(value = "bairro", required = false) String bairro,
            @RequestParam(value = "destaque", required = false) Boolean destaque,
            @RequestParam(value = "condicoesEspeciais", required = false) Boolean condicoesEspecias,
            @RequestParam(value = "idUsuario", required = false) Long idUsuario,
            @PageableDefault(page = 0, size = 10) Pageable pageable,
            @RequestParam(value = "ativo") Boolean ativo
    ) {


        Page<Imovel> paginaResultadoDto = service.pesquisa(descricao,tamanhoMinimo,tamanhoMaximo, titulo, tipoResidencia, qtdBanheiros, qtdQuartos,

                qtdGaragens, precoMin, precoMax, finalidade,cidade,bairro, ativo,destaque,condicoesEspecias,idUsuario, pageable);



        return ResponseEntity.ok(paginaResultadoDto.map(imovelGetMapper::toImovelListagemDto));
    }

    @GetMapping("{id}")
    public ResponseEntity<ImovelGetDTO> buscarPorId(@PathVariable Long id){
        return ResponseEntity.ok(imovelGetMapper.toImovelGetDto(service.buscarPorId(id)));
    }
    @PreAuthorize("hasAnyRole('ADMINISTRADOR','EDITOR')")
    @PostMapping
    public ResponseEntity<ImovelGetDTO> cadastrar(@RequestPart("imovel") String imovelPostDtoJSON,
                                                  @RequestPart("imagens") List<MultipartFile> imagens,
                                                  @RequestPart("imagemPrincipal") MultipartFile imagemPrincipal
    ) throws IOException, MethodArgumentNotValidException {


        ObjectMapper mapper = new ObjectMapper();
        ImovelPostDTO imovelPostDTO = mapper.readValue(imovelPostDtoJSON, ImovelPostDTO.class);

        dtoValidator.validaDTO(ImovelPostDTO.class,imovelPostDTO,"ImovelPostDto");

        System.out.println(imovelPostDTO + "\n");

        return ResponseEntity.ok(imovelGetMapper.toImovelGetDto(service.salvar(imovelPostDTO,imagemPrincipal, imagens)));

    }
    @PutMapping("{id}")
    public ResponseEntity<ImovelGetDTO> atualizar(@RequestPart("imovel") String imovelPutDTOJSON,
                                                  @RequestParam(value = "refImagensDeletadas", required = false) List<String> refImagensDeletadas,                                                  @RequestPart(value = "imagemPrincipal", required = false) MultipartFile imagemCapa,
                                                  @RequestPart(value = "imagens", required = false) List<MultipartFile> imagens,
                                                  @PathVariable Long id) throws IOException, MethodArgumentNotValidException {


        ObjectMapper mapper = new ObjectMapper();
        ImovelPutDTO imovelPutDTO = mapper.readValue(imovelPutDTOJSON, ImovelPutDTO.class);
        dtoValidator.validaDTO(ImovelPutDTO.class, imovelPutDTO, "ImovelPutDTO");

        return ResponseEntity.ok(imovelGetMapper.toImovelGetDto(service.atualizar(id, imovelPutDTO, imagemCapa, imagens,refImagensDeletadas)));

    }
    @PostMapping("/restaurar/{id}")
    public ResponseEntity<Void> restaurarImagem(@PathVariable Long id){
        this.service.restaurarImagem(id);
        return ResponseEntity.ok().build();

    }


    @DeleteMapping("{id}")
    public ResponseEntity<Void> removerPorId(@PathVariable Long id){
        service.removerPorId(id);
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/imagem/{id}")
    public ResponseEntity<Void> removerImagemPorReferencia(
            @PathVariable Long id){

        this.service.removerImagemPorIdImagem(id);

        return ResponseEntity.noContent().build();
    }
    @GetMapping("/ids-imoveis")
    public ResponseEntity<List<Long>> listarImoveis(){
        return  ResponseEntity.ok(service.buscarTodosIds());
    }



}
