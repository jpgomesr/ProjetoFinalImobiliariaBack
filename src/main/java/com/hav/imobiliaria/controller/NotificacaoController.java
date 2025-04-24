package com.hav.imobiliaria.controller;

import com.hav.imobiliaria.controller.dto.notificacao.NotificacaoResponseDTO;
import com.hav.imobiliaria.model.entity.Notificacao;
import com.hav.imobiliaria.service.NotificacaoService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notificacao")
@AllArgsConstructor
public class NotificacaoController {
    private final NotificacaoService notificacaoService;

    @PostMapping("/{idUsuario}")
    public ResponseEntity<NotificacaoResponseDTO> criarNotificacao(
            @RequestBody Notificacao notificacao,
            @PathVariable Long idUsuario
    ) {
        return notificacaoService.criarNotificacao(notificacao, idUsuario);
    }

    @GetMapping("/{idUsuario}")
    public ResponseEntity<List<NotificacaoResponseDTO>> notificar(@PathVariable Long idUsuario)
            throws InterruptedException {
        return notificacaoService.buscarNovasNotificacoes(idUsuario);
    }

    @GetMapping("/list/{idUsuario}")
    public ResponseEntity<List<NotificacaoResponseDTO>> listar(@PathVariable Long idUsuario) {
        return notificacaoService.listar(idUsuario);
    }

    @PostMapping("/ler/{idNotificacao}")
    public ResponseEntity<NotificacaoResponseDTO> ler(@PathVariable Long idNotificacao) {
        return notificacaoService.ler(idNotificacao);
    }
}