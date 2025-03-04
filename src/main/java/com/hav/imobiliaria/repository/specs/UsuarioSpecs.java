package com.hav.imobiliaria.repository.specs;


import com.hav.imobiliaria.model.Usuario;
import org.springframework.data.jpa.domain.Specification;

public class UsuarioSpecs {

    public static Specification<Usuario> nomeLike(String nome) {
        return (root, query, cb) -> cb.like(cb.upper(root.get("titulo")), "%" + nome.toUpperCase() + "%");
    }

    public static Specification<Usuario> usuarioAtivo(Boolean ativo) {
        return (root, query, cb) -> cb.equal(root.get("ativo"), ativo);
    }

    public static Specification<Usuario> roleUsuario(String role) {
        return (root, query, cb) -> cb.equal(root.get("role"), role);
    }
    public static Specification<Usuario> naoDeletado() {
        return (root, query, cb) -> cb.equal(root.get("deletado"), false);
    }

}
