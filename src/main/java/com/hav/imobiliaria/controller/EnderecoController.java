package com.hav.imobiliaria.controller;

import com.hav.imobiliaria.repository.EnderecoRepository;
import com.hav.imobiliaria.service.EnderecoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("enderecos")
@AllArgsConstructor
@Tag(name = "Endereços", description = "Operações relacionadas a endereços")
public class EnderecoController {

    private final EnderecoService service;

    @PreAuthorize("permitAll()")
    @GetMapping("cidades/{estado}")
    @Operation(summary = "Buscar cidades por estado", description = "Retorna uma lista de cidades disponíveis para um estado específico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de cidades obtida com sucesso"),
        @ApiResponse(responseCode = "404", description = "Estado não encontrado", content = @Content)
    })
    public ResponseEntity<Set<String>> buscarCidades(
            @Parameter(description = "Nome do estado (use hífen no lugar de espaços)", required = true) 
            @PathVariable String estado) {
        String estadoFormatado = estado.replace("-"," ");
        return ResponseEntity.ok(service.buscarCidades(estadoFormatado));
    }
    
    @PreAuthorize("permitAll()")
    @GetMapping("bairros/{cidade}")
    @Operation(summary = "Buscar bairros por cidade", description = "Retorna uma lista de bairros disponíveis para uma cidade específica")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de bairros obtida com sucesso"),
        @ApiResponse(responseCode = "404", description = "Cidade não encontrada", content = @Content)
    })
    public ResponseEntity<Set<String>> buscarBairros(
            @Parameter(description = "Nome da cidade (use hífen no lugar de espaços)", required = true) 
            @PathVariable String cidade) {
        String cidadeFormatada = cidade.replace("-"," ");

        return ResponseEntity.ok(this.service.buscarBarrosPorCidade(cidadeFormatada));
    }
}
