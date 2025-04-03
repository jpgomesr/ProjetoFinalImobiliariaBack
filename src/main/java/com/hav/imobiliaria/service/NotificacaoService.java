package com.hav.imobiliaria.service;

import com.hav.imobiliaria.controller.dto.notificacao.NotificacaoResponseDTO;
import com.hav.imobiliaria.controller.mapper.notificacao.NotificacaoResponseMapper;
import com.hav.imobiliaria.model.entity.Notificacao;
import com.hav.imobiliaria.repository.NotificacaoRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class NotificacaoService {
    private NotificacaoRepository notificacaoRepository;
    private NotificacaoResponseMapper notificacaoResponseMapper;

    private UsuarioService usuarioService;

    public NotificacaoResponseDTO criarNotificacao(Notificacao notificacao, Long idUsuario) {
        notificacao.setUsuario(usuarioService.buscarPorId(idUsuario));
        Notificacao notificacaoSave = notificacaoRepository.save(notificacao);
        return notificacaoResponseMapper.toDTO(notificacaoSave);
    }

    public ResponseEntity<List<NotificacaoResponseDTO>> listar(Long idUsuario) {
        List<NotificacaoResponseDTO> notificacoesDto =
                notificacaoRepository.findAllByUsuario_Id(idUsuario)
                        .stream().map(notificacao ->
                                notificacaoResponseMapper.toDTO(notificacao))
                        .toList();
        return ResponseEntity.ok(notificacoesDto);
    }

    public ResponseEntity<List<NotificacaoResponseDTO>> ler(Long idUsuario) {
        List<Notificacao> notificacoes = notificacaoRepository.findAllByUsuario_Id(idUsuario);
        notificacoes.forEach(notificacao -> {
            notificacao.setLido(true);
        });
        return ResponseEntity.ok(notificacaoRepository.saveAll(notificacoes).stream().map(
                notificacao -> {
                    return notificacaoResponseMapper.toDTO(notificacao);
                }
        ).toList());
    }
}
