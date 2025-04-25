package com.hav.imobiliaria.controller;

import com.hav.imobiliaria.service.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/messages")
@AllArgsConstructor
@Tag(name = "Mensagens", description = "Operações relacionadas a mensagens de chat")
public class MessageController {
    private final MessageService service;

    @PutMapping("/marcar-lidas/{chatId}/{usuarioId}")
    @Operation(summary = "Marcar mensagens como lidas", description = "Marca todas as mensagens de um chat como lidas para um usuário específico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Mensagens marcadas como lidas com sucesso"),
        @ApiResponse(responseCode = "404", description = "Chat ou usuário não encontrado", content = @Content)
    })
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<Void> marcarMensagensComoLidas(
            @Parameter(description = "ID do chat", required = true) 
            @PathVariable Long chatId,
            @Parameter(description = "ID do usuário", required = true) 
            @PathVariable Long usuarioId) {
        service.marcarMensagensComoLidas(chatId, usuarioId);
        return ResponseEntity.ok().build();
    }
}
