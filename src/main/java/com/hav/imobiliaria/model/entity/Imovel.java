package com.hav.imobiliaria.model.entity;

import com.hav.imobiliaria.model.enums.TipoBunnerEnum;
import com.hav.imobiliaria.model.enums.TipoFinalidadeEnum;
import com.hav.imobiliaria.model.enums.TipoImovelEnum;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table
@Data
@EntityListeners(AuditingEntityListener.class)
public class  Imovel {

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

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "id_imovel")
    private List<ImagemImovel> imagens;

    private Double iptu;

    private Double valorCondominio;

    @CreatedDate
    private LocalDateTime dataCadastro;



    @ManyToOne
    @JoinColumn(name = "id_proprietario", nullable = false)
    private Proprietario proprietario;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "id_endereco", nullable = false)
    private Endereco endereco;

    @ManyToMany()
    @JoinTable(
            name = "imovel_corretor",
            joinColumns = @JoinColumn(name = "id_imovel"),
            inverseJoinColumns = @JoinColumn(name = "id_corretor")
    )
    private List<Corretor> corretores;


    @Column
    private Boolean ativo;

    @Column
    private LocalDateTime dataDelecao;

    @ManyToMany(mappedBy = "imoveisFavoritados", fetch = FetchType.LAZY)
    private List<Usuario> usuariosFavoritos;

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
        if(this.ativo == null) {
            ativo = true;
        }
    }

}
