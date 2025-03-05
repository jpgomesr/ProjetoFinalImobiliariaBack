package com.hav.imobiliaria.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table
@Data
public class    Imovel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 45)
    private String titulo;

    @Column(nullable = false, length = 500)
    private String descricao;

    @Column(nullable = false)
    private Integer tamanho;

    @Column(nullable = false)
    private Integer qtdQuartos;

    @Column(nullable = false)
    private Integer qtdBanheiros;

    private Integer qtdGaragens;

    @Column
    private Integer qtdChurrasqueira;

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


    @Column(nullable = false)
    private Boolean banner;

    @Enumerated(EnumType.STRING)
    private TipoBunnerEnum tipoBanner;

    @OneToMany
    @JoinColumn(name = "id_imovel")
    private List<ImagemImovel> imagens;

    private Double iptu;

    private Double valorCondominio;

    @ManyToOne
    @JoinColumn(name = "id_proprietario", nullable = false)
    private Proprietario proprietario;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "id_endereco", nullable = false)
    private Endereco endereco;

    @Column
    private Boolean deletado;

    @Column
    private LocalDateTime dataDelecao;


    public void addImagem(ImagemImovel imagem) {
        this.imagens.add(imagem);
    }
    public void removeImagem(ImagemImovel imagem) {
        this.imagens.remove(imagem);
    }
    public Integer getQuantidadeImagens() {
        return this.imagens.size();
    }

    @PrePersist
    public void prePersist() {
        if(this.deletado == null) {
            deletado = false;
        }
    }

}
