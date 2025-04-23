package com.hav.imobiliaria.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_senha_antiga")
@Data
@EntityListeners(AuditingEntityListener.class)
public class SenhaAntigaUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 500)
    private String senha;

    @ManyToOne
    private Usuario usuario;

    @CreatedDate
    private LocalDateTime dataCadastro;




}
