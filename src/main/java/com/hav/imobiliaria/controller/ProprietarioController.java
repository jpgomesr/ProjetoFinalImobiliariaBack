package com.hav.imobiliaria.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hav.imobiliaria.controller.dto.proprietario.*;
import com.hav.imobiliaria.controller.mapper.proprietario.ProprietarioListaSelectResponseMapper;
import com.hav.imobiliaria.controller.mapper.proprietario.ProprietarioRespostaUnicaMapper;
import com.hav.imobiliaria.controller.mapper.proprietario.ProprietarioListagemMapper;
import com.hav.imobiliaria.model.entity.Proprietario;
import com.hav.imobiliaria.service.ProprietarioService;
import com.hav.imobiliaria.validator.DtoValidator;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/proprietarios")
@AllArgsConstructor
public class ProprietarioController implements GenericController {

    private final ProprietarioService service;
    private final DtoValidator dtoValidator;
    private final ProprietarioRespostaUnicaMapper proprietarioRespostaUnicaMapper;
    private final ProprietarioListagemMapper proprietarioListagemMapper;
    private final ProprietarioListaSelectResponseMapper proprietarioListaSelectResponseMapper;


    @GetMapping
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'EDITOR')")
    public ResponseEntity<Page<ProprietarioListagemDTO>> listarProprietarios(
            @RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "cpf", required = false) String cpf,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "ativo", required = false) Boolean ativo,

            Pageable pageable
    ) {

        Page<Proprietario> paginaResultado = service.pesquisa(nome, cpf, email,ativo,pageable);
        return ResponseEntity.ok(paginaResultado.map(proprietarioListagemMapper::toDTO));
    }


    @PreAuthorize("hasAnyRole('ADMINISTRADOR','EDITOR')")
    @GetMapping("{id}")
    public ResponseEntity<ProprietarioRespostaUnicaDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(proprietarioRespostaUnicaMapper.toDto(service.buscarPorId(id)));
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR','EDITOR')")
    @PostMapping
    public ResponseEntity<ProprietarioRespostaUnicaDTO> cadastrar(@RequestPart("proprietario") String proprietarioPostDTOJSON,
                                                                  @RequestPart(value = "foto",required = false) MultipartFile foto) throws IOException, MethodArgumentNotValidException {


        ObjectMapper objectMapper = new ObjectMapper();
        ProprietarioPostDTO proprietarioPostDTO = objectMapper.readValue(proprietarioPostDTOJSON, ProprietarioPostDTO.class);

        this.dtoValidator.validaDTO(ProprietarioPostDTO.class,proprietarioPostDTO, "proprietario");


        return ResponseEntity.ok(proprietarioRespostaUnicaMapper.toDto(service.salvar(proprietarioPostDTO, foto)));
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR','EDITOR')")
    @PutMapping("{id}")
    public ResponseEntity<ProprietarioRespostaUnicaDTO> atualizar(@RequestPart("proprietario") String proprietarioPutDTOJSON,
                                                                  @RequestPart(value = "foto", required = false) MultipartFile foto,
                                                                  @PathVariable Long id) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        ProprietarioPutDTO proprietarioPutDTO = objectMapper.readValue(proprietarioPutDTOJSON, ProprietarioPutDTO.class);


        return ResponseEntity.ok(proprietarioRespostaUnicaMapper.toDto(service.atualizar(proprietarioPutDTO, foto, id)));
    }
    @PreAuthorize("hasAnyRole('ADMINISTRADOR','EDITOR')")
    @DeleteMapping("{id}")
    public ResponseEntity<Void> removerPorId(@PathVariable Long id) {
        service.removerPorId(id);
        return ResponseEntity.noContent().build();
    }
    @PreAuthorize("hasAnyRole('ADMINISTRADOR','EDITOR')")
    @PostMapping("/restaurar/{id}")
    public ResponseEntity<Void> restaurarUsuario(@PathVariable Long id) {
        this.service.restaurarUsuario(id);

        return  ResponseEntity.ok().build();
    }
    @PreAuthorize("hasAnyRole('ADMINISTRADOR','EDITOR')")
    @GetMapping("/lista-select")
    public ResponseEntity<List<ProprietarioListaSelectResponseDTO>> listarProprietarios(){
        return  ResponseEntity.ok(this.service.buscarTodos().stream().map(proprietarioListaSelectResponseMapper::toDto).toList());
    }
    @PreAuthorize("permitAll()")
    @GetMapping("lista-id-proprietarios")
    public ResponseEntity<List<Long>> listarIdUsuario(){
        return ResponseEntity.ok(service.buscarIdProprietarios());
    }





}
