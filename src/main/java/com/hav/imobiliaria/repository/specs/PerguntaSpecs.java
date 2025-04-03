package com.hav.imobiliaria.repository.specs;

import com.hav.imobiliaria.model.entity.Pergunta;
import com.hav.imobiliaria.model.enums.TipoPerguntaEnum;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class PerguntaSpecs {
    public static Specification<Pergunta> tipoPerguntaLike(TipoPerguntaEnum tipoPergunta) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.upper(root.get("tipo_pergunta")),
                        "%" + tipoPergunta.toString().toUpperCase() + "%");
    }
    public static Specification<Pergunta> emailLike(String email){
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.upper(root.get("email")),
                        "%" + email.toUpperCase() + "%");
    }
    public static Specification<Pergunta> tituloLike(String titulo){
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("mensagem"),
                        "%" + titulo.toUpperCase() + "%");
    }
    public static Specification<Pergunta> mensagemLike(String mensagem){
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("mensagem"),
                        "%" + mensagem + "%");
    }
    public static Specification<Pergunta> dataLike(LocalDateTime data){
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("data"), "%" + data + "%");
    }
}
