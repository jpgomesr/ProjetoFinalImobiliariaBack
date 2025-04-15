package com.hav.imobiliaria.repository.specs;


import com.hav.imobiliaria.model.entity.Usuario;
import com.hav.imobiliaria.model.enums.RoleEnum;
import org.springframework.data.jpa.domain.Specification;

public class UsuarioSpecs {

    public static Specification<Usuario> nomeLike(String nome) {
        return (root, query, cb) -> cb.or(
        cb.like(cb.upper(root.get("nome")), "%" + nome.toUpperCase() + "%"),
        cb.like(cb.upper(root.get("email")), "%" + nome.toUpperCase() + "%")
        );
    }

    public static Specification<Usuario> usuarioAtivo(Boolean ativo) {
        return (root, query, cb) -> cb.equal(root.get("ativo"), ativo);
    }

    public static Specification<Usuario> roleUsuario(RoleEnum role) {
        return (root, query, cb) -> cb.equal(root.get("role"), role);
    }


}
