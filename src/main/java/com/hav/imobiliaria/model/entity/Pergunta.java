package com.hav.imobiliaria.model.entity;

import com.hav.imobiliaria.model.enums.TipoPerguntaEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class  Pergunta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "tipo_pergunta")
    private TipoPerguntaEnum tipoPergunta;
    private String email;
    private String telefone;
    private String nome;
    private String mensagem;
    @CreatedDate
    private LocalDateTime data;
    private String resposta;

    @OneToOne(cascade = CascadeType.ALL)
    private Administrador idAdministrador;
    @OneToOne(cascade = CascadeType.ALL)
    private Editor idEditor;


}
