package com.hav.imobiliaria.service;

import com.hav.imobiliaria.controller.dto.notificacao.NotificacaoResponseDTO;
import com.hav.imobiliaria.controller.mapper.notificacao.NotificacaoResponseMapper;
import com.hav.imobiliaria.model.entity.Notificacao;
import com.hav.imobiliaria.repository.NotificacaoRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.*;

@Service
@AllArgsConstructor
public class NotificacaoService {
    private NotificacaoRepository notificacaoRepository;
    private NotificacaoResponseMapper notificacaoResponseMapper;
    private UsuarioService usuarioService;

    private final Map<Long, BlockingQueue<NotificacaoResponseDTO>> notificacaoMap = new ConcurrentHashMap<>();
    private final Map<Long, RateLimiter> rateLimiters = new ConcurrentHashMap<>();

    private static final long TIMEOUT_NOTIFICACAO = 30L;
    private static final int MAX_NOTIFICACOES_POR_MINUTO = 5;
    private static final long JANELA_TEMPO_RATE_LIMIT = 60_000L; // 1 minuto em milissegundos

    private static class RateLimiter {
        private final Queue<Long> timestamps = new ConcurrentLinkedQueue<>();
        private final int maxRequests;
        private final long timeWindowMs;

        public RateLimiter(int maxRequests, long timeWindowMs) {
            this.maxRequests = maxRequests;
            this.timeWindowMs = timeWindowMs;
        }

        public synchronized boolean permitirRequisicao() {
            long agora = System.currentTimeMillis();
            while (!timestamps.isEmpty() && agora - timestamps.peek() > timeWindowMs) {
                timestamps.poll();
            }

            if (timestamps.size() < maxRequests) {
                timestamps.offer(agora);
                return true;
            }
            return false;
        }

        public synchronized void removerUltimaRequisicao() {
            timestamps.poll();
        }
    }

    public ResponseEntity<NotificacaoResponseDTO> criarNotificacao(Notificacao notificacao, Long idUsuario) {
        RateLimiter limiter = rateLimiters.computeIfAbsent(idUsuario,
                k -> new RateLimiter(MAX_NOTIFICACOES_POR_MINUTO, JANELA_TEMPO_RATE_LIMIT));

        if (!limiter.permitirRequisicao()) {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
        }

        try {
            // Verifica notificações duplicadas não lidas
            List<NotificacaoResponseDTO> notificacoesExistentes = listar(idUsuario).getBody();
            if (notificacoesExistentes != null) {
                boolean duplicada = notificacoesExistentes.stream()
                        .anyMatch(n -> compararNotificacoes(n, notificacao) && !n.lido());
                
                if (duplicada) {
                    limiter.removerUltimaRequisicao();
                    return ResponseEntity.status(HttpStatus.CONFLICT).build();
                }
            }

            notificacao.setUsuario(usuarioService.buscarPorId(idUsuario));
            notificacao.setLido(false);
            Notificacao notificacaoSalva = notificacaoRepository.save(notificacao);
            NotificacaoResponseDTO responseDTO = notificacaoResponseMapper.toDTO(notificacaoSalva);

            // Adiciona à fila de notificações do usuário
            notificacaoMap.computeIfAbsent(idUsuario, k -> new LinkedBlockingQueue<>())
                    .offer(responseDTO);

            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            limiter.removerUltimaRequisicao();
            throw e;
        }
    }

    public ResponseEntity<List<NotificacaoResponseDTO>> buscarNovasNotificacoes(Long idUsuario) 
            throws InterruptedException {
        List<NotificacaoResponseDTO> notificacoes = new ArrayList<>();
        BlockingQueue<NotificacaoResponseDTO> filaUsuario = notificacaoMap.get(idUsuario);

        if (filaUsuario != null) {
            NotificacaoResponseDTO primeiraNotificacao = filaUsuario.poll(TIMEOUT_NOTIFICACAO, TimeUnit.SECONDS);
            if (primeiraNotificacao != null) {
                notificacoes.add(primeiraNotificacao);
                filaUsuario.drainTo(notificacoes);
            }
        }

        return ResponseEntity.ok(notificacoes);
    }

    public ResponseEntity<List<NotificacaoResponseDTO>> listar(Long idUsuario) {
        List<NotificacaoResponseDTO> notificacoesDto = notificacaoRepository
                .findAllByUsuario_Id(idUsuario)
                .stream()
                .map(notificacaoResponseMapper::toDTO)
                .toList();
        return ResponseEntity.ok(notificacoesDto);
    }

    public ResponseEntity<NotificacaoResponseDTO> ler(Long idNotificacao) {
        Notificacao notificacao = notificacaoRepository.findById(idNotificacao).get();
        notificacao.setLido(true);
        
        return ResponseEntity.ok(notificacaoResponseMapper.toDTO(notificacaoRepository.save(notificacao)));
    }

    private boolean compararNotificacoes(NotificacaoResponseDTO existente, Notificacao nova) {
        return existente.titulo().trim().toLowerCase().equals(nova.getTitulo().trim().toLowerCase()) &&
               existente.descricao().trim().toLowerCase().equals(nova.getDescricao().trim().toLowerCase());
    }
}
