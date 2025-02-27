package com.hav.imobiliaria.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "imagem_imovel")
@Data
public class ImagemImovel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(length = 150, nullable = false, unique = true)
    private String referencia;

    @Column(nullable = false)
    private Boolean imagemCapa;


}