package com.hav.imobiliaria.model.entity;

import com.hav.imobiliaria.model.enums.TipoMensagem;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String conteudo;
    private String remetente;
    private TipoMensagem tipoMensagem;
    private LocalDateTime timeStamp;

    public ChatMessage(String conteudo, String remetente, TipoMensagem tipoMensagem) {
        this.conteudo = conteudo;
        this.remetente = remetente;
        this.tipoMensagem = tipoMensagem;
        this.timeStamp = LocalDateTime.now();
    }
}
