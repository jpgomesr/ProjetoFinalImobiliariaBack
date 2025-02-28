package com.hav.imobiliaria.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Proprietario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 45)
    private String nome;

    @Column(nullable = false, length = 45)
    private String email;

    @Column(nullable = false, unique = true, length = 11)
    private String telefone;

    @Column(nullable = false, unique = true, length = 11)
    private String cpf;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "id_endereco", nullable = false)
    private Endereco endereco;
}
