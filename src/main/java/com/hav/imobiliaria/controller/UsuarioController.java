package com.hav.imobiliaria.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hav.imobiliaria.controller.dto.imovel.ImovelPostDTO;
import com.hav.imobiliaria.controller.dto.usuario.UsuarioGetDTO;
import com.hav.imobiliaria.controller.dto.usuario.UsuarioPostDTO;
import com.hav.imobiliaria.controller.dto.usuario.UsuarioPutDTO;
import com.hav.imobiliaria.controller.mapper.usuario.UsuarioGetMapper;
import com.hav.imobiliaria.controller.mapper.usuario.UsuarioPostMapper;
import com.hav.imobiliaria.controller.mapper.usuario.UsuarioPutMapper;
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
    private final UsuarioGetMapper usuarioGetMapper;
    private final UsuarioPostMapper usuarioPostMapper;
    private final UsuarioPutMapper usuarioPutMapper;

    @GetMapping
    public ResponseEntity<Page<UsuarioGetDTO>> listarEmPaginas(Pageable pageable) {
        return ResponseEntity.ok(service.buscarTodos(pageable));
    }
    @GetMapping("{id}")
    public ResponseEntity<UsuarioGetDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioGetMapper.toDto(service.buscarPorId(id)));
    }
    @PostMapping
    public ResponseEntity<UsuarioGetDTO> cadastrar(@RequestPart(value = "usuario") String usuarioJson,
                                                   @RequestPart(value = "file", required = false) MultipartFile file) throws IOException, MethodArgumentNotValidException {

        ObjectMapper mapper = new ObjectMapper();
        UsuarioPostDTO usuarioPostDTO = mapper.readValue(usuarioJson, UsuarioPostDTO.class);

        this.dtoValidator.validaDTO(UsuarioPostDTO.class, usuarioPostDTO,"usuarioPostDTO");


        return ResponseEntity.ok(this.usuarioGetMapper.toDto
                (service.salvar(usuarioPostDTO,file)));


    }
    @PutMapping("{id}")
    public ResponseEntity<UsuarioGetDTO> atualizar(@RequestPart String usuario,
                                                   @RequestPart(required = false) MultipartFile novaImagem,
                                                   @PathVariable Long id ) throws IOException, MethodArgumentNotValidException {

        ObjectMapper mapper = new ObjectMapper();
        UsuarioPutDTO usuarioPutDTO = mapper.readValue(usuario, UsuarioPutDTO.class);

        this.dtoValidator.validaDTO(UsuarioPutDTO.class, usuarioPutDTO,"usuarioPutDTO");


        return ResponseEntity.ok(this.usuarioGetMapper.toDto(service.atualizar(usuarioPutDTO,id, novaImagem)));
    }
    @DeleteMapping("{id}")
    public ResponseEntity<Void> removerPorId(@PathVariable Long id) {
        service.removerPorId(id);
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/imagem/{id}")
    public ResponseEntity<Void> removerImagemUsuario(@PathVariable Long idUsuario){
        this.service.removerImagemUsuario(idUsuario);
        return ResponseEntity.noContent().build();
    }


}
