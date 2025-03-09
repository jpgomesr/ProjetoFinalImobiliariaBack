package com.hav.imobiliaria.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hav.imobiliaria.controller.dto.imovel.ImovelGetDTO;
import com.hav.imobiliaria.controller.dto.imovel.ImovelPostDTO;
import com.hav.imobiliaria.controller.dto.imovel.ImovelPutDTO;
import com.hav.imobiliaria.model.enums.TipoFinalidadeEnum;
import com.hav.imobiliaria.model.enums.TipoImovelEnum;
import com.hav.imobiliaria.service.ImovelService;
import com.hav.imobiliaria.validator.DtoValidator;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
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

    @GetMapping
    public ResponseEntity<Page<ImovelGetDTO>> listarImoveis(
            @RequestParam(value = "descricao", required = false) String descricao,
            @RequestParam(value = "tamanho", required = false) Integer tamanho,
            @RequestParam(value = "titulo", required = false) String titulo,
            @RequestParam(value = "tipoResidencia", required = false) TipoImovelEnum tipoResidencia,
            @RequestParam(value = "qtdQuartos", required = false) Integer qtdQuartos,
            @RequestParam(value = "qtdBanheiros", required = false) Integer qtdBanheiros,
            @RequestParam(value = "qtdGaragens", required = false) Integer qtdGaragens,
            @RequestParam(value = "precoMin", required = false) Double precoMin,
            @RequestParam(value = "precoMax", required = false) Double precoMax,
            @RequestParam(value = "finalidade", required = false) TipoFinalidadeEnum finalidade,
            @RequestParam(value = "cidade", required = false) String cidade,
            @RequestParam(value = "bairro", required = false) String bairro,
            @RequestParam(value = "pagina", defaultValue = "0") Integer pagina,
            @RequestParam(value = "tamanho-pagina", defaultValue = "10") Integer tamanhoPagina
    ) {


        Page<ImovelGetDTO> paginaResultadoDto = service.pesquisa(descricao,tamanho, titulo, tipoResidencia, qtdBanheiros, qtdQuartos,
                qtdGaragens, precoMin, precoMax, finalidade,cidade,bairro, pagina, tamanhoPagina);


        return ResponseEntity.ok(paginaResultadoDto);
    }

    @GetMapping("{id}")
    public ResponseEntity<ImovelGetDTO> buscarPorId(@PathVariable Long id){
        return ResponseEntity.ok(service.buscarPorId(id));
    }
    @PostMapping
    public ResponseEntity<ImovelGetDTO> cadastrar(@RequestPart("imovel") String imovelPostDtoJSON,
                                                  @RequestPart("imagens") List<MultipartFile> imagens,
                                                  @RequestPart("imagemPrincipal") MultipartFile imagemPrincipal
    ) throws IOException, MethodArgumentNotValidException {



        ObjectMapper mapper = new ObjectMapper();
        ImovelPostDTO imovelPostDTO = mapper.readValue(imovelPostDtoJSON, ImovelPostDTO.class);

        dtoValidator.validaDTO(ImovelPostDTO.class,imovelPostDTO,"ImovelPostDto");

        return ResponseEntity.ok(service.salvar(imovelPostDTO,imagemPrincipal, imagens));

    }
    @PutMapping("{id}")
    public ResponseEntity<ImovelGetDTO> atualizar(@RequestPart("imovel") String imovelPutDTOJSON,
                                                  @RequestPart(value = "imagemPrincipal", required = false) MultipartFile imagemCapa,
                                                  @RequestPart(value = "imagens", required = false) List<MultipartFile> imagens,
                                                  @PathVariable Long id) throws IOException, MethodArgumentNotValidException {


        ObjectMapper mapper = new ObjectMapper();
        ImovelPutDTO imovelPutDTO = mapper.readValue(imovelPutDTOJSON, ImovelPutDTO.class);
        dtoValidator.validaDTO(ImovelPutDTO.class, imovelPutDTO, "ImovelPutDTO");
        ImovelGetDTO imovelAtualizado = service.atualizar(id, imovelPutDTO, imagemCapa, imagens);

        return ResponseEntity.ok(imovelAtualizado);

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
}
