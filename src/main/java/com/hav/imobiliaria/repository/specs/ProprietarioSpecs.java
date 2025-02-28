package com.hav.imobiliaria.repository.specs;

import com.hav.imobiliaria.model.Imovel;
import com.hav.imobiliaria.model.Proprietario;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class ProprietarioSpecs {

    public static Specification<Proprietario> nomeLike(String nome) {
        return (root, query, cb) -> cb.like(cb.upper(root.get("nome")), "%" + nome.toUpperCase() + "%");
    }


    public static Specification<Proprietario> cpfLike(String cpf) {
        return (root, query, cb) -> cb.like(cb.upper(root.get("cpf")), "%" + cpf.toUpperCase() + "%");
    }

    public static Specification<Proprietario> emailLike(String email) {
        return (root, query, cb) -> cb.like(cb.upper(root.get("email")), "%" + email.toUpperCase() + "%");
    }
}
