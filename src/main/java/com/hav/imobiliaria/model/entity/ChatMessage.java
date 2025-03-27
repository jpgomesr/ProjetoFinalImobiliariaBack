package com.hav.imobiliaria.model.entity;

import jakarta.persistence.*;
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
    private LocalDateTime timeStamp;
    private Boolean lida;
    @ManyToOne
    @JoinColumn(name = "chat_id", nullable = false)
    private Chats chat;

    public ChatMessage(String conteudo, String remetente, Chats chat) {
        this.conteudo = conteudo;
        this.remetente = remetente;
        this.timeStamp = LocalDateTime.now();
        this.lida = false;
        this.chat = chat;
    }
}
