package com.hav.imobiliaria.repository.specs;

import com.hav.imobiliaria.model.entity.Proprietario;
import org.springframework.data.jpa.domain.Specification;

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
    public static Specification<Proprietario> ativo(Boolean ativo) {
        return (root, query, cb) -> cb.equal(root.get("ativo"), ativo );
    }


}
