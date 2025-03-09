package com.hav.imobiliaria.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hav.imobiliaria.controller.dto.proprietario.ProprietarioRespostaUnicaDTO;
import com.hav.imobiliaria.controller.dto.proprietario.ProprietarioListagemDTO;
import com.hav.imobiliaria.controller.dto.proprietario.ProprietarioPostDTO;
import com.hav.imobiliaria.controller.dto.proprietario.ProprietarioPutDTO;
import com.hav.imobiliaria.controller.mapper.proprietario.ProprietarioRespostaUnicaMapper;
import com.hav.imobiliaria.controller.mapper.proprietario.ProprietarioListagemMapper;
import com.hav.imobiliaria.model.Proprietario;
import com.hav.imobiliaria.service.ProprietarioService;
import com.hav.imobiliaria.validator.DtoValidator;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/proprietarios")
@AllArgsConstructor
public class ProprietarioController implements GenericController {

    private final ProprietarioService service;
    private final DtoValidator dtoValidator;
    private final ProprietarioRespostaUnicaMapper proprietarioRespostaUnicaMapper;
    private final ProprietarioListagemMapper proprietarioListagemMapper;


    @GetMapping
    public ResponseEntity<Page<ProprietarioListagemDTO>> listarProprietarios(
            @RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "cpf", required = false) String cpf,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "pagina", defaultValue = "0") Integer pagina,
            @RequestParam(value = "tamanho-pagina", defaultValue = "10") Integer tamanhoPagina
    ) {

        Page<Proprietario> paginaResultado = service.pesquisa(nome, cpf, email,pagina,tamanhoPagina);
        return ResponseEntity.ok(paginaResultado.map(proprietarioListagemMapper::toDTO));
    }


    @GetMapping("{id}")
    public ResponseEntity<ProprietarioRespostaUnicaDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(proprietarioRespostaUnicaMapper.toDto(service.buscarPorId(id)));
    }

    @PostMapping
    public ResponseEntity<ProprietarioRespostaUnicaDTO> cadastrar(@RequestPart("proprietario") String proprietarioPostDTOJSON,
                                                                  @RequestPart(value = "foto",required = false) MultipartFile foto) throws IOException, MethodArgumentNotValidException {


        ObjectMapper objectMapper = new ObjectMapper();
        ProprietarioPostDTO proprietarioPostDTO = objectMapper.readValue(proprietarioPostDTOJSON, ProprietarioPostDTO.class);

        this.dtoValidator.validaDTO(ProprietarioPostDTO.class,proprietarioPostDTO, "proprietario");


        return ResponseEntity.ok(proprietarioRespostaUnicaMapper.toDto(service.salvar(proprietarioPostDTO, foto)));
    }

    @PutMapping("{id}")
    public ResponseEntity<ProprietarioRespostaUnicaDTO> atualizar(@RequestPart("proprietario") String proprietarioPutDTOJSON,
                                                                  @RequestPart(value = "foto", required = false) MultipartFile foto,
                                                                  @PathVariable Long id) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        ProprietarioPutDTO proprietarioPutDTO = objectMapper.readValue(proprietarioPutDTOJSON, ProprietarioPutDTO.class);


        return ResponseEntity.ok(proprietarioRespostaUnicaMapper.toDto(service.atualizar(proprietarioPutDTO, foto, id)));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> removerPorId(@PathVariable Long id) {
        service.removerPorId(id);
        return ResponseEntity.noContent().build();
    }
    @PostMapping("/restaurar/{id}")
    public ResponseEntity<Void> restaurarUsuario(@PathVariable Long id) {
        this.service.restaurarUsuario(id);

        return  ResponseEntity.ok().build();
    }





}
