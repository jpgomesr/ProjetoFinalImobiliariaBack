package com.hav.imobiliaria.controller;

import com.hav.imobiliaria.controller.dto.agendamento.HorarioCorretorPatchDTO;
import com.hav.imobiliaria.controller.dto.agendamento.HorarioCorretorPostDTO;
import com.hav.imobiliaria.service.HorarioCorretorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("horarios/corretor")
@AllArgsConstructor
@Tag(name = "Horários de Corretores", description = "Operações relacionadas a horários disponíveis dos corretores")
public class HorarioCorretorController {

    private final HorarioCorretorService service;

    @PreAuthorize("hasRole('CORRETOR')")
    @PostMapping
    @Operation(summary = "Salvar horário", description = "Registra um novo horário disponível para um corretor")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Horário registrado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content),
        @ApiResponse(responseCode = "403", description = "Acesso negado", content = @Content)
    })
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<Void> salvarHorario(
            @Parameter(description = "Dados do horário a ser registrado", required = true) 
            @RequestBody @Valid HorarioCorretorPostDTO horarioCorretorPostDTO) {

        this.service.save(horarioCorretorPostDTO);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    
    @PreAuthorize("hasRole('CORRETOR')")
    @DeleteMapping("{id}")
    @Operation(summary = "Deletar horário", description = "Remove um horário disponível de um corretor")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Horário removido com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso negado", content = @Content),
        @ApiResponse(responseCode = "404", description = "Horário não encontrado", content = @Content)
    })
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<Void> deletarHorario(
            @Parameter(description = "ID do horário", required = true) 
            @PathVariable Long id) {

        this.service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    @PreAuthorize("hasRole('CORRETOR')")
    @PatchMapping
    @Operation(summary = "Atualizar horário", description = "Atualiza um horário disponível de um corretor")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Horário atualizado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content),
        @ApiResponse(responseCode = "403", description = "Acesso negado", content = @Content),
        @ApiResponse(responseCode = "404", description = "Horário não encontrado", content = @Content)
    })
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<Void> atualizarHorario(
            @Parameter(description = "Dados atualizados do horário", required = true) 
            @RequestBody @Valid HorarioCorretorPatchDTO horarioCorretorPatchDTO) {
        this.service.atualizar(horarioCorretorPatchDTO);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
