package com.hav.imobiliaria.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table
@Data
public class Imovel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 45)
    private String titulo;

    @Column(nullable = false, length = 100)
    private String imagemCapa;

    @Column(nullable = false, length = 45)
    private String descricao;

    @Column(nullable = false)
    private Integer tamanho;

    @Column(nullable = false)
    private Integer qtdQuartos;

    @Column(nullable = false)
    private Integer qtdBanheiros;

    private Integer qtdGaragens;

    @Column(length = 45)
    private String qtdChurrasqueira;

    private Integer qtdPiscina;

    @Column(nullable = false)
    private TipoFinalidadeEnum finalidade;

    private Boolean academia;

    @Column(nullable = false)
    private Double preco;

    private Double precoPromocional;

    @Column(nullable = false)
    private Boolean permitirDestaque;

    @Column(nullable = false)
    private Boolean habilitarVisibilidade;

    @Column(nullable = false, length = 8)
    private String cep;

    @Column(nullable = false, length = 45)
    private String tipoResidencia;

    @Column(nullable = false)
    private Integer numeroCasaPredio;

    private Integer numeroApartamento;

    @Column(nullable = false)
    private Boolean banner;

    @Enumerated(EnumType.STRING)
    private TipoBunnerEnum tipoBanner;

    @Column(nullable = false, length = 45)
    private String bairro;

    @Column(nullable = false, length = 45)
    private String cidade;

    @Column(nullable = false, length = 45)
    private String estado;

    @Column(nullable = false, length = 255)
    private String galeriaImagens;

    private Double iptu;

    private Double valorCondominio;

    @ManyToOne
    @JoinColumn(name = "id_proprietario", nullable = false)
    private Proprietario proprietario;

}
