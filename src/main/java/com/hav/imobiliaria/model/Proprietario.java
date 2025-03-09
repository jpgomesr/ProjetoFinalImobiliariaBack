package com.hav.imobiliaria.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Proprietario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 45)
    private String nome;

    @Column(nullable = false, length = 11)
    private String telefone;

    @Column(nullable = false, length = 11)
    private String celular;

    @Column(nullable = false, unique = true, length = 11)
    private String cpf;

    @Column(nullable = false, unique = true, length = 200)
    private String email;


    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "id_endereco", nullable = false)
    private Endereco endereco;

    @Column
    private Boolean deletado;

    @Column
    private LocalDateTime dataDelecao;

    @Column(length = 150)
    private String imagemUrl;

    @PrePersist
    public void prePersist() {
        if(this.deletado == null) {
            deletado = false;
        }
    }
}
