package com.hav.imobiliaria.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hav.imobiliaria.controller.dto.imovel.ImovelPostDTO;
import com.hav.imobiliaria.controller.dto.usuario.UsuarioGetDTO;
import com.hav.imobiliaria.controller.dto.usuario.UsuarioPostDTO;
import com.hav.imobiliaria.controller.dto.usuario.UsuarioPutDTO;
import com.hav.imobiliaria.service.UsuarioService;
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
import java.util.Set;

@RestController
@RequestMapping("/usuarios")
@AllArgsConstructor
public class UsuarioController implements GenericController{

    private UsuarioService service;
    private DtoValidator  dtoValidator;

    @GetMapping
    public ResponseEntity<Page<UsuarioGetDTO>> listarEmPaginas(Pageable pageable) {
        return ResponseEntity.ok(service.buscarTodos(pageable));
    }
    @GetMapping("{id}")
    public ResponseEntity<UsuarioGetDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }
    @PostMapping
    public ResponseEntity<UsuarioGetDTO> cadastrar(@RequestPart(value = "usuario") String usuarioJson,
                                                   @RequestPart(value = "file", required = false) MultipartFile file) throws IOException, MethodArgumentNotValidException {

        ObjectMapper mapper = new ObjectMapper();
        UsuarioPostDTO usuarioPostDTO = mapper.readValue(usuarioJson, UsuarioPostDTO.class);

        this.dtoValidator.validaDTO(UsuarioPostDTO.class, usuarioPostDTO,"usuarioPostDTO");


        return ResponseEntity.ok(service.salvar(usuarioPostDTO,file));




    }
    @PutMapping("{id}")
    public ResponseEntity<UsuarioGetDTO> atualizar(@RequestPart String usuarioPutDtoJSON,
                                                   @RequestPart MultipartFile novaImagem,
                                                   @PathVariable Long id) throws JsonProcessingException, MethodArgumentNotValidException {

        ObjectMapper mapper = new ObjectMapper();
        UsuarioPutDTO usuarioPutDTO = mapper.readValue(usuarioPutDtoJSON, UsuarioPutDTO.class);

        this.dtoValidator.validaDTO(UsuarioPutDTO.class, usuarioPutDTO,"usuarioPutDTO");


        return ResponseEntity.ok(service.atualizar(usuarioPutDTO,id));
    }
    @DeleteMapping("{id}")
    public ResponseEntity<Void> removerPorId(@PathVariable Long id) {
        service.removerPorId(id);
        return ResponseEntity.noContent().build();
    }


}
