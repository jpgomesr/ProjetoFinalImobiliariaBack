package com.hav.imobiliaria.repository.specs;

import com.hav.imobiliaria.model.entity.Proprietario;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

public class ProprietarioSpecs {

    public static Specification<Proprietario> nomeEmailLike(String pesquisa) {
        return (root, query, cb) -> {
            Predicate nomeLike = cb.like(cb.upper(root.get("nome")), "%" + pesquisa.toUpperCase() + "%");
            Predicate emailLike = cb.like(cb.upper(root.get("email")), "%" + pesquisa.toUpperCase() + "%");

            return cb.or(nomeLike,emailLike);
        };
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
