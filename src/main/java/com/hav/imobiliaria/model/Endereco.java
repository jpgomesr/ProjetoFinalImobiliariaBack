package com.hav.imobiliaria.model;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table
@Data
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 45)
    private String bairro;
    @Column(nullable = false, length = 45)
    private String cidade;
    @Column(nullable = false, length = 45)
    private String estado;
    @Column(nullable = false, length = 45)
    private String rua;
    @Column(nullable = false, length = 8)
    private String cep;
    @Column(nullable = false)
    private Integer numeroCasaPredio;
    private Integer numeroApartamento;



}
