package com.hav.imobiliaria.controller;

import com.hav.imobiliaria.controller.dto.agendamento.AgendamentoListagemDTO;
import com.hav.imobiliaria.controller.dto.agendamento.AgendamentoPostDto;
import com.hav.imobiliaria.controller.dto.agendamento.AgendamentoPutDTO;
import com.hav.imobiliaria.controller.dto.agendamento.HorarioCorretorPostDTO;
import com.hav.imobiliaria.controller.dto.usuario.UsuarioNomeIdDTO;
import com.hav.imobiliaria.controller.mapper.endereco.EnderecoGetMapper;
import com.hav.imobiliaria.model.entity.Agendamento;
import com.hav.imobiliaria.model.enums.StatusAgendamentoEnum;
import com.hav.imobiliaria.service.AgendamentoService;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/agendamentos")
@AllArgsConstructor
@Tag(name = "Agendamentos", description = "Operações relacionadas a agendamentos de visitas a imóveis")
public class AgendamentosController {

    private final AgendamentoService service;
    private final EnderecoGetMapper enderecoGetMapper;


    @PreAuthorize("hasAnyRole('USUARIO')")
    @PostMapping
    @Operation(summary = "Agendar visita", description = "Cria um novo agendamento de visita a um imóvel")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Agendamento criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content),
        @ApiResponse(responseCode = "403", description = "Acesso negado", content = @Content),
        @ApiResponse(responseCode = "404", description = "Imóvel ou corretor não encontrado", content = @Content)
    })
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<Void> agendarVisita(
            @Parameter(description = "Dados do agendamento", required = true) 
            @RequestBody AgendamentoPostDto agendamentoPostDto) {
        this.service.salvarAgendamento(agendamentoPostDto);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    
    @PreAuthorize("hasAnyRole('USUARIO')")
    @PutMapping
    @Operation(summary = "Atualizar visita", description = "Atualiza um agendamento de visita existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Agendamento atualizado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content),
        @ApiResponse(responseCode = "403", description = "Acesso negado", content = @Content),
        @ApiResponse(responseCode = "404", description = "Agendamento não encontrado", content = @Content)
    })
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<Void> atualizarVisita(
            @Parameter(description = "Dados atualizados do agendamento", required = true) 
            @RequestBody AgendamentoPutDTO agendamentoPutDTO) {
        this.service.atualizarAgendamento(agendamentoPutDTO);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('CORRETOR', 'ADMINISTRADOR', 'EDITOR')")
    @GetMapping("/corretor/{id}")
    @Operation(summary = "Listar agendamentos por corretor", description = "Retorna uma lista paginada de agendamentos de um corretor específico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Agendamentos encontrados com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso negado", content = @Content),
        @ApiResponse(responseCode = "404", description = "Corretor não encontrado", content = @Content)
    })
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<Page<AgendamentoListagemDTO>> buscarAgendamentos(
            @Parameter(description = "ID do corretor", required = true) 
            @PathVariable Long id,
            @Parameter(description = "Status do agendamento") 
            @RequestParam(required = false, name = "status") StatusAgendamentoEnum status,
            @Parameter(description = "Data do agendamento") 
            @RequestParam(required = false) LocalDate data,
            @Parameter(description = "Configuração de paginação") 
            Pageable pageable) {

        Page<Agendamento> agendamentos = service.listarAgendamentosCorretorId(
                pageable, id, status, data);

        Page<AgendamentoListagemDTO> agendamentoListagemDTOS = agendamentos.map(agendamento -> {
            return new AgendamentoListagemDTO(
                    agendamento.getId(),
                    agendamento.getDataHora(),
                    enderecoGetMapper.toEnderecoVisitaDTO(agendamento.getImovel().getEndereco()),
                    new UsuarioNomeIdDTO(agendamento.getUsuarioComum().getId(), agendamento.getUsuarioComum().getNome()),
                    new UsuarioNomeIdDTO(agendamento.getCorretor().getId(), agendamento.getCorretor().getNome()),
                    agendamento.getImovel().getId(),
                    agendamento.getImovel().getImagens().getFirst().getReferencia(),
                    agendamento.getStatus());
        });

        return ResponseEntity.ok(agendamentoListagemDTOS);
    }

    @PreAuthorize("hasAnyRole('USUARIO')")
    @GetMapping("/usuario/{id}")
    @Operation(summary = "Listar agendamentos por usuário", description = "Retorna uma lista paginada de agendamentos de um usuário específico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Agendamentos encontrados com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso negado", content = @Content),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content)
    })
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<Page<AgendamentoListagemDTO>> buscarAgendamentosUsuario(
            @Parameter(description = "ID do usuário", required = true) 
            @PathVariable Long id,
            @Parameter(description = "Status do agendamento") 
            @RequestParam(required = false, name = "status") StatusAgendamentoEnum status,
            @Parameter(description = "Data do agendamento") 
            @RequestParam(required = false) LocalDate data,
            @Parameter(description = "Configuração de paginação") 
            Pageable pageable) {

        Page<Agendamento> agendamentos = service.listarAgendamentosUsuarioId(
                pageable, id, status, data);

        Page<AgendamentoListagemDTO> agendamentoListagemDTOS = agendamentos.map(agendamento -> {
            return new AgendamentoListagemDTO(
                    agendamento.getId(),
                    agendamento.getDataHora(),
                    enderecoGetMapper.toEnderecoVisitaDTO(agendamento.getImovel().getEndereco()),
                    new UsuarioNomeIdDTO(agendamento.getUsuarioComum().getId(), agendamento.getUsuarioComum().getNome()),
                    new UsuarioNomeIdDTO(agendamento.getCorretor().getId(), agendamento.getCorretor().getNome()),
                    agendamento.getImovel().getId(),
                    agendamento.getImovel().getImagens().getFirst().getReferencia(),
                    agendamento.getStatus());
        });

        return ResponseEntity.ok(agendamentoListagemDTOS);
    }

    @PreAuthorize("hasAnyRole('CORRETOR','USUARIO')")
    @PatchMapping("{id}")
    @Operation(summary = "Alterar status do agendamento", description = "Atualiza o status de um agendamento existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Status alterado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Status inválido", content = @Content),
        @ApiResponse(responseCode = "403", description = "Acesso negado", content = @Content),
        @ApiResponse(responseCode = "404", description = "Agendamento não encontrado", content = @Content)
    })
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<Void> alterarStatus(
            @Parameter(description = "ID do agendamento", required = true) 
            @PathVariable Long id,
            @Parameter(description = "Novo status do agendamento", required = true) 
            @RequestParam StatusAgendamentoEnum status) {

        this.service.atualizarStatus(id, status);

        return ResponseEntity.noContent().build();
    }
}
