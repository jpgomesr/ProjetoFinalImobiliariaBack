package com.hav.imobiliaria.model.entity;

import jakarta.persistence.*;
import lombok.*;

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
    @Column(name = "id_chat")
    private Long idChat;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usuario1_id", nullable = false)
    private Usuario usuario1;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usuario2_id", nullable = false)
    private Usuario usuario2;
    @OneToMany(mappedBy = "chat", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<ChatMessage> messages;
}
