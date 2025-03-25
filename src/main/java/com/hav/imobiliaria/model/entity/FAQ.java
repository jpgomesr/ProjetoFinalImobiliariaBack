package com.hav.imobiliaria.model.entity;

import com.hav.imobiliaria.model.enums.TipoPerguntaEnum;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FAQ {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private TipoPerguntaEnum tipoPergunta;
    private String email;
    private String telefone;
    private String nome;
    private String mensagem;


}
