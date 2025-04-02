package com.hav.imobiliaria.controller;

import com.hav.imobiliaria.service.MessageService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/messages")
@AllArgsConstructor
public class MessageController {
    private final MessageService service;

    @PutMapping("/marcar-lidas/{chatId}/{usuarioId}")
    public ResponseEntity<Void> marcarMensagensComoLidas(@PathVariable Long chatId, @PathVariable Long usuarioId) {
        service.marcarMensagensComoLidas(chatId, usuarioId);
        return ResponseEntity.ok().build();
    }
}
