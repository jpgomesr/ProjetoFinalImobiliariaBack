package com.hav.imobiliaria.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 45)
    private String role;

    @Column(nullable = false, length = 45)
    private String nome;

    @Column(length = 13)
    private String telefone;

    @Column(nullable = false, length = 45)
    private String email;

    @Column(length = 500)
    private String descricao;

    @Column(length = 100)
    private String foto;
}
