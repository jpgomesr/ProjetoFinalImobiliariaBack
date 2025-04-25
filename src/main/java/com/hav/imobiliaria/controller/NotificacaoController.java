package com.hav.imobiliaria.controller;

import com.hav.imobiliaria.controller.dto.notificacao.NotificacaoResponseDTO;
import com.hav.imobiliaria.model.entity.Notificacao;
import com.hav.imobiliaria.service.NotificacaoService;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notificacao")
@AllArgsConstructor
@Tag(name = "Notificações", description = "Operações relacionadas a notificações dos usuários")
public class NotificacaoController {
    private final NotificacaoService notificacaoService;

    @PostMapping("/{idUsuario}")
    @Operation(summary = "Criar notificação", description = "Cria uma nova notificação para um usuário específico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Notificação criada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content)
    })
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<NotificacaoResponseDTO> criarNotificacao(
            @Parameter(description = "Dados da notificação", required = true) 
            @RequestBody Notificacao notificacao,
            @Parameter(description = "ID do usuário", required = true) 
            @PathVariable Long idUsuario
    ) {
        return notificacaoService.criarNotificacao(notificacao, idUsuario);
    }

    @GetMapping("/{idUsuario}")
    @Operation(summary = "Buscar novas notificações", description = "Retorna a lista de novas notificações de um usuário")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Notificações encontradas com sucesso"),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content)
    })
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<List<NotificacaoResponseDTO>> notificar(
            @Parameter(description = "ID do usuário", required = true) 
            @PathVariable Long idUsuario) throws InterruptedException {
        return notificacaoService.buscarNovasNotificacoes(idUsuario);
    }

    @GetMapping("/list/{idUsuario}")
    @Operation(summary = "Listar notificações", description = "Retorna todas as notificações de um usuário")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de notificações obtida com sucesso"),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content)
    })
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<List<NotificacaoResponseDTO>> listar(
            @Parameter(description = "ID do usuário", required = true) 
            @PathVariable Long idUsuario) {
        return notificacaoService.listar(idUsuario);
    }

    @PostMapping("/ler/{idNotificacao}")
    @Operation(summary = "Marcar notificação como lida", description = "Marca uma notificação específica como lida")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Notificação marcada como lida com sucesso"),
        @ApiResponse(responseCode = "404", description = "Notificação não encontrada", content = @Content)
    })
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<NotificacaoResponseDTO> ler(
            @Parameter(description = "ID da notificação", required = true) 
            @PathVariable Long idNotificacao) {
        return notificacaoService.ler(idNotificacao);
    }
}