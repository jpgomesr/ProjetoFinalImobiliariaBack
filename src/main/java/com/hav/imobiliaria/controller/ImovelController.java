package com.hav.imobiliaria.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hav.imobiliaria.controller.dto.imovel.ImovelGetDTO;
import com.hav.imobiliaria.controller.dto.imovel.ImovelPostDTO;
import com.hav.imobiliaria.controller.dto.imovel.ImovelPutDTO;
import com.hav.imobiliaria.controller.mapper.imovel.ImovelGetMapper;
import com.hav.imobiliaria.controller.mapper.imovel.ImovelPostMapper;
import com.hav.imobiliaria.controller.mapper.imovel.ImovelPostMapper;
import com.hav.imobiliaria.controller.mapper.imovel.ImovelPutMapper;
import com.hav.imobiliaria.model.Imovel;
import com.hav.imobiliaria.model.TipoFinalidadeEnum;
import com.hav.imobiliaria.service.ImovelService;
import com.hav.imobiliaria.validator.DtoValidator;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("imoveis")
@AllArgsConstructor
public class ImovelController implements GenericController {
    private final ImovelService service;
    private final DtoValidator dtoValidator;
    private final ImovelPostMapper imovelPostMapper;
    private final ImovelGetMapper imovelGetMapper;

    @GetMapping
    public ResponseEntity<Page<ImovelGetDTO>> listarImoveis(
            @RequestParam(value = "titulo", required = false) String titulo,
            @RequestParam(value = "tipoResidencia", required = false) String tipoResidencia,
            @RequestParam(value = "qtdQuartos", required = false) Integer qtdQuartos,
            @RequestParam(value = "qtdBanheiros", required = false) Integer qtdBanheiros,
            @RequestParam(value = "qtdGaragens", required = false) Integer qtdGaragens,
            @RequestParam(value = "precoMin", required = false) Double precoMin,
            @RequestParam(value = "precoMax", required = false) Double precoMax,
            @RequestParam(value = "finalidade", required = false) TipoFinalidadeEnum finalidade,
            @RequestParam(value = "pagina", defaultValue = "0") Integer pagina,
            @RequestParam(value = "tamanho-pagina", defaultValue = "10") Integer tamanhoPagina
    ) {
        System.out.println("qtdQuartos recebido: " + qtdQuartos);
        // Criando filtros conforme os parâmetros fornecidos
        Page<ImovelGetDTO> paginaResultadoDto = service.pesquisa(titulo, tipoResidencia, qtdQuartos, qtdBanheiros,
                qtdGaragens, precoMin, precoMax, finalidade, pagina, tamanhoPagina);

        // Convertendo a lista de Imovel para DTO
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

        return ResponseEntity.ok(imovelGetMapper.toDto(service.salvar(imovelPostDTO,imagemPrincipal, imagens)));

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
