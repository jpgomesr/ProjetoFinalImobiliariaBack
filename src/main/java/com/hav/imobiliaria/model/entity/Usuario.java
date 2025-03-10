package com.hav.imobiliaria.model.entity;

import com.hav.imobiliaria.model.enums.RoleEnum;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "role", discriminatorType = DiscriminatorType.STRING)
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(insertable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    private RoleEnum role;

    @Column(nullable = false, length = 45)
    private String nome;

    @Column(length = 13, unique = true)
    private String telefone;

    @Column
    private String senha;

    @Column(nullable = false, length = 45, unique = true)
    private String email;

    @Column(length = 500)
    private String descricao;

    @Column(length = 300)
    private String foto;

    @Column(columnDefinition = "BOOLEAN DEFAULT true")
    private Boolean ativo;

    @Column
    private Boolean deletado;

    @Column
    private LocalDateTime dataDelecao;

    @PrePersist
    public void prePersist() {
        if (ativo == null) {
            ativo = true;
        }
        if (deletado == null) {
            deletado = false;
        }
    }
}