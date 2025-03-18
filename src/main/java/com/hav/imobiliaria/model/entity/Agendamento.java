package com.hav.imobiliaria.model.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "agendamento")
public class Agendamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private LocalDateTime dataHora;

    @OneToOne
    @JoinColumn(name = "id_imovel")
    private Imovel imovel;

    @ManyToOne
    @JoinColumn(name = "id_corretor")
    private Corretor corretor;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private UsuarioComum usuarioComum;

    
}
