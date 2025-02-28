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

    @GetMapping
    public ResponseEntity<Page<ImovelGetDTO>> listarImoveis(Pageable pageable) {
        return ResponseEntity.ok(service.buscarTodos(pageable));
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
