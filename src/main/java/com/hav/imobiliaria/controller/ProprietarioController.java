package com.hav.imobiliaria.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hav.imobiliaria.controller.dto.proprietario.*;
import com.hav.imobiliaria.controller.mapper.proprietario.ProprietarioListaSelectResponseMapper;
import com.hav.imobiliaria.controller.mapper.proprietario.ProprietarioRespostaUnicaMapper;
import com.hav.imobiliaria.controller.mapper.proprietario.ProprietarioListagemMapper;
import com.hav.imobiliaria.model.entity.Proprietario;
import com.hav.imobiliaria.service.ProprietarioService;
import com.hav.imobiliaria.validator.DtoValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Proprietários", description = "Operações relacionadas a proprietários de imóveis")
public class ProprietarioController implements GenericController {

    private final ProprietarioService service;
    private final DtoValidator dtoValidator;
    private final ProprietarioRespostaUnicaMapper proprietarioRespostaUnicaMapper;
    private final ProprietarioListagemMapper proprietarioListagemMapper;
    private final ProprietarioListaSelectResponseMapper proprietarioListaSelectResponseMapper;


    @GetMapping
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'EDITOR')")
    @Operation(summary = "Listar proprietários", description = "Retorna uma lista paginada de proprietários com base nos filtros fornecidos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Proprietários encontrados com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso negado", content = @Content),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = @Content)
    })
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<Page<ProprietarioListagemDTO>> listarProprietarios(
            @Parameter(description = "Nome do proprietário para busca") 
            @RequestParam(value = "nome", required = false) String nome,
            @Parameter(description = "CPF do proprietário para busca") 
            @RequestParam(value = "cpf", required = false) String cpf,
            @Parameter(description = "Email do proprietário para busca") 
            @RequestParam(value = "email", required = false) String email,
            @Parameter(description = "Filtrar por status ativo") 
            @RequestParam(value = "ativo", required = false) Boolean ativo,
            @Parameter(description = "Configuração de paginação") 
            Pageable pageable
    ) {

        Page<Proprietario> paginaResultado = service.pesquisa(nome, cpf, email,ativo,pageable);
        return ResponseEntity.ok(paginaResultado.map(proprietarioListagemMapper::toDTO));
    }


    @PreAuthorize("hasAnyRole('ADMINISTRADOR','EDITOR')")
    @GetMapping("{id}")
    @Operation(summary = "Buscar proprietário por ID", description = "Retorna os detalhes de um proprietário específico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Proprietário encontrado com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso negado", content = @Content),
        @ApiResponse(responseCode = "404", description = "Proprietário não encontrado", content = @Content)
    })
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<ProprietarioRespostaUnicaDTO> buscarPorId(
            @Parameter(description = "ID do proprietário", required = true) 
            @PathVariable Long id) {
        return ResponseEntity.ok(proprietarioRespostaUnicaMapper.toDto(service.buscarPorId(id)));
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR','EDITOR')")
    @PostMapping
    @Operation(summary = "Cadastrar proprietário", description = "Cadastra um novo proprietário no sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Proprietário cadastrado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content),
        @ApiResponse(responseCode = "403", description = "Acesso negado", content = @Content)
    })
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<ProprietarioRespostaUnicaDTO> cadastrar(
            @Parameter(description = "Dados do proprietário em formato JSON", required = true) 
            @RequestPart("proprietario") String proprietarioPostDTOJSON,
            @Parameter(description = "Foto do proprietário") 
            @RequestPart(value = "foto",required = false) MultipartFile foto) throws IOException, MethodArgumentNotValidException {


        ObjectMapper objectMapper = new ObjectMapper();
        ProprietarioPostDTO proprietarioPostDTO = objectMapper.readValue(proprietarioPostDTOJSON, ProprietarioPostDTO.class);

        this.dtoValidator.validaDTO(ProprietarioPostDTO.class,proprietarioPostDTO, "proprietario");


        return ResponseEntity.ok(proprietarioRespostaUnicaMapper.toDto(service.salvar(proprietarioPostDTO, foto)));
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR','EDITOR')")
    @PutMapping("{id}")
    @Operation(summary = "Atualizar proprietário", description = "Atualiza os dados de um proprietário existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Proprietário atualizado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content),
        @ApiResponse(responseCode = "403", description = "Acesso negado", content = @Content),
        @ApiResponse(responseCode = "404", description = "Proprietário não encontrado", content = @Content)
    })
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<ProprietarioRespostaUnicaDTO> atualizar(
            @Parameter(description = "Dados atualizados do proprietário em formato JSON", required = true) 
            @RequestPart("proprietario") String proprietarioPutDTOJSON,
            @Parameter(description = "Nova foto do proprietário") 
            @RequestPart(value = "foto", required = false) MultipartFile foto,
            @Parameter(description = "ID do proprietário", required = true) 
            @PathVariable Long id) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        ProprietarioPutDTO proprietarioPutDTO = objectMapper.readValue(proprietarioPutDTOJSON, ProprietarioPutDTO.class);


        return ResponseEntity.ok(proprietarioRespostaUnicaMapper.toDto(service.atualizar(proprietarioPutDTO, foto, id)));
    }
    
    @PreAuthorize("hasAnyRole('ADMINISTRADOR','EDITOR')")
    @DeleteMapping("{id}")
    @Operation(summary = "Remover proprietário", description = "Remove um proprietário pelo seu ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Proprietário removido com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso negado", content = @Content),
        @ApiResponse(responseCode = "404", description = "Proprietário não encontrado", content = @Content)
    })
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<Void> removerPorId(
            @Parameter(description = "ID do proprietário", required = true) 
            @PathVariable Long id) {
        service.removerPorId(id);
        return ResponseEntity.noContent().build();
    }
    
    @PreAuthorize("hasAnyRole('ADMINISTRADOR','EDITOR')")
    @PostMapping("/restaurar/{id}")
    @Operation(summary = "Restaurar proprietário", description = "Restaura um proprietário previamente removido")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Proprietário restaurado com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso negado", content = @Content),
        @ApiResponse(responseCode = "404", description = "Proprietário não encontrado", content = @Content)
    })
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<Void> restaurarUsuario(
            @Parameter(description = "ID do proprietário", required = true) 
            @PathVariable Long id) {
        this.service.restaurarUsuario(id);

        return ResponseEntity.ok().build();
    }
    
    @PreAuthorize("hasAnyRole('ADMINISTRADOR','EDITOR')")
    @GetMapping("/lista-select")
    @Operation(summary = "Listar proprietários para seleção", description = "Retorna uma lista simplificada de proprietários para seleção em formulários")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista obtida com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso negado", content = @Content)
    })
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<List<ProprietarioListaSelectResponseDTO>> listarProprietarios() {
        return ResponseEntity.ok(this.service.buscarTodos().stream().map(proprietarioListaSelectResponseMapper::toDto).toList());
    }
    
    @PreAuthorize("permitAll()")
    @GetMapping("lista-id-proprietarios")
    @Operation(summary = "Listar IDs de proprietários", description = "Retorna uma lista com todos os IDs de proprietários cadastrados")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de IDs obtida com sucesso")
    })
    public ResponseEntity<List<Long>> listarIdUsuario() {
        return ResponseEntity.ok(service.buscarIdProprietarios());
    }
}
