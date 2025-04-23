package com.hav.imobiliaria.model.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "tb_senha_antiga")
@Data
public class SenhaAntigaUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 500)
    private String senha;

    @ManyToOne()
    private Usuario usuario;

}
