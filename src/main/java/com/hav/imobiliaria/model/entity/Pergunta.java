package com.hav.imobiliaria.model.entity;

import com.hav.imobiliaria.model.enums.TipoPerguntaEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pergunta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "tipo_pergunta")
    private TipoPerguntaEnum tipoPergunta;
    private String email;
    private String telefone;
    private String nome;
    private String mensagem;


}
