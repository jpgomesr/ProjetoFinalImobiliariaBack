package com.hav.imobiliaria.controller;

import com.hav.imobiliaria.controller.dto.notificacao.NotificacaoResponseDTO;
import com.hav.imobiliaria.model.entity.Notificacao;
import com.hav.imobiliaria.service.NotificacaoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.*;

@RestController
@RequestMapping("/notificacao")
@AllArgsConstructor
public class NotificacaoController {
    private final NotificacaoService notificacaoService;

    private final Map<Long, BlockingQueue<NotificacaoResponseDTO>> notificacaoMap = new ConcurrentHashMap<>();
    private final Map<Long, NotificacaoResponseDTO> ultimaNotificacaoMap = new ConcurrentHashMap<>();
    private final Map<Long, Long> ultimaNotificacaoTimestamp = new ConcurrentHashMap<>();
    private final Map<Long, RateLimiter> rateLimiters = new ConcurrentHashMap<>();

    private static final long INTERVALO_MINIMO_MS = 10_000; // 10 segundos
    private static final int MAX_NOTIFICACOES_POR_MINUTO = 5; // Máximo de 5 notificações por minuto por usuário

    private static class RateLimiter {
        private final Queue<Long> timestamps = new ConcurrentLinkedQueue<>();
        private final int maxRequests;
        private final long timeWindowMs;

        public RateLimiter(int maxRequests, long timeWindowMs) {
            this.maxRequests = maxRequests;
            this.timeWindowMs = timeWindowMs;
        }

        public synchronized boolean allowRequest() {
            long now = System.currentTimeMillis();
            // Remove timestamps mais antigos que a janela de tempo
            while (!timestamps.isEmpty() && now - timestamps.peek() > timeWindowMs) {
                timestamps.poll();
            }

            if (timestamps.size() < maxRequests) {
                timestamps.offer(now);
                return true;
            }
            return false;
        }
    }

    @PostMapping("/{idUsuario}")
    public ResponseEntity<NotificacaoResponseDTO> criarNotificacao(
            @RequestBody Notificacao notificacao,
            @PathVariable Long idUsuario
    ) {
        synchronized (this) {
            // Verifica rate limiting
            RateLimiter limiter = rateLimiters.computeIfAbsent(idUsuario,
                    k -> new RateLimiter(MAX_NOTIFICACOES_POR_MINUTO, 60_000));

            if (!limiter.allowRequest()) {
                return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
            }

            // Primeiro, verifica se já existe uma notificação não lida com o mesmo conteúdo
            ResponseEntity<List<NotificacaoResponseDTO>> ultimasNotificacoesResponse = notificacaoService.listar(idUsuario);
            List<NotificacaoResponseDTO> ultimasNotificacoes = ultimasNotificacoesResponse.getBody();
            
            String tituloAtual = notificacao.getTitulo().trim().toLowerCase();
            String descricaoAtual = notificacao.getDescricao().trim().toLowerCase();
            
            if (ultimasNotificacoes != null && !ultimasNotificacoes.isEmpty()) {
                Optional<NotificacaoResponseDTO> notificacaoExistenteOpt = ultimasNotificacoes.stream()
                    .filter(n -> n.titulo().trim().toLowerCase().equals(tituloAtual) &&
                                n.descricao().trim().toLowerCase().equals(descricaoAtual) &&
                                !n.lido())
                    .findFirst();
                
                if (notificacaoExistenteOpt.isPresent()) {
                    // Se já existe uma notificação não lida com o mesmo conteúdo, retorna ela
                    limiter.timestamps.poll(); // Remove o timestamp para não contar no rate limit
                    return ResponseEntity.status(HttpStatus.CONFLICT)
                            .body(notificacaoExistenteOpt.get());
                }
            }

            // Se não existe, cria uma nova notificação
            NotificacaoResponseDTO notificacaoResponseDTO = notificacaoService.criarNotificacao(notificacao, idUsuario);

            // Atualiza os registros
            ultimaNotificacaoMap.put(idUsuario, notificacaoResponseDTO);
            ultimaNotificacaoTimestamp.put(idUsuario, System.currentTimeMillis());

            // Adiciona à fila
            notificacaoMap.computeIfAbsent(idUsuario, k -> new LinkedBlockingQueue<>())
                    .offer(notificacaoResponseDTO);

            return ResponseEntity.ok(notificacaoResponseDTO);
        }
    }

    @GetMapping("/{idUsuario}")
    public ResponseEntity<List<NotificacaoResponseDTO>> notificar(@PathVariable Long idUsuario)
            throws InterruptedException {
        List<NotificacaoResponseDTO> notificacoes = new ArrayList<>();
        BlockingQueue<NotificacaoResponseDTO> filaUsuario = notificacaoMap.get(idUsuario);

        if (filaUsuario != null) {
            NotificacaoResponseDTO primeiraNotificacao = filaUsuario.poll(30, TimeUnit.SECONDS);
            if (primeiraNotificacao != null) {
                notificacoes.add(primeiraNotificacao);
                filaUsuario.drainTo(notificacoes);
            }
        }

        return ResponseEntity.ok(notificacoes);
    }

    @GetMapping("/list/{idUsuario}")
    public ResponseEntity<List<NotificacaoResponseDTO>> listar(@PathVariable Long idUsuario) {
        return notificacaoService.listar(idUsuario);
    }

    @PostMapping("/ler/{idUsuario}")
    public ResponseEntity<List<NotificacaoResponseDTO>> ler(@PathVariable Long idUsuario) {
        return notificacaoService.ler(idUsuario);
    }
}