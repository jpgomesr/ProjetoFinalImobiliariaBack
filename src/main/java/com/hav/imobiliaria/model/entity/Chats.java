package com.hav.imobiliaria.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Chats {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long idChat;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usuario1_id", nullable = false)
    private Usuario usuario1;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usuario2_id", nullable = false)
    private Usuario usuario2;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<ChatMessage> messages;
}
