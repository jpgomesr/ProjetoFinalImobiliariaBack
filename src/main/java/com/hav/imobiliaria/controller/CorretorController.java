package com.hav.imobiliaria.controller;

import com.hav.imobiliaria.controller.dto.agendamento.HorarioCorretorGetDTO;
import com.hav.imobiliaria.controller.mapper.agendamento.HorarioCorretorGetMapper;
import com.hav.imobiliaria.model.entity.HorarioCorretor;
import com.hav.imobiliaria.model.entity.Imovel;
import com.hav.imobiliaria.service.CorretorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("corretores")
@AllArgsConstructor
@Tag(name = "Corretores", description = "Operações relacionadas aos corretores")
public class CorretorController {

    private final CorretorService service;
    private final HorarioCorretorGetMapper horarioCorretorGetMapper;

    @PreAuthorize("hasRole('USUARIO')")
    @GetMapping("/horarios/{id}")
    @Operation(summary = "Buscar horários por imóvel", description = "Retorna os horários disponíveis dos corretores para um imóvel específico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Horários encontrados com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso negado", content = @Content),
        @ApiResponse(responseCode = "404", description = "Imóvel não encontrado", content = @Content)
    })
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<List<HorarioCorretorGetDTO>> getHorariosPorIdImovel(
            @Parameter(description = "ID do imóvel", required = true) 
            @PathVariable Long id,
            @Parameter(description = "Mês (número de 1 a 12)") 
            @RequestParam(required = false) Integer mes,
            @Parameter(description = "Dia do mês") 
            @RequestParam(required = false) Integer dia) {

        return ResponseEntity.ok(this.service.buscarHorariosCorretoresPorIdImovel(id, mes, dia)
                .stream().map(horarioCorretorGetMapper::toDTO).toList());
    }
    
    @PreAuthorize("hasRole('CORRETOR')")
    @GetMapping("/horarios/corretor")
    @Operation(summary = "Buscar horários do corretor logado", description = "Retorna os horários registrados do corretor autenticado")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Horários encontrados com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso negado", content = @Content),
        @ApiResponse(responseCode = "404", description = "Corretor não encontrado", content = @Content)
    })
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<List<HorarioCorretorGetDTO>> getHorarios() {
        return ResponseEntity.ok(this.service.buscarHorariosCorretoresPorId().stream().map(horarioCorretorGetMapper::toDTO).toList());
    }
}
