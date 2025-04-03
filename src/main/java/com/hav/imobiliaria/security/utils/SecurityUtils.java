package com.hav.imobiliaria.security.utils;

import com.hav.imobiliaria.exceptions.AcessoNegadoException;
import com.hav.imobiliaria.model.entity.Usuario;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {

    public static void verificarUsuarioLogado(Long idUsuario) {
        Usuario principal = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!principal.getId().equals(idUsuario)){
            throw new AcessoNegadoException();
        }
    }
    public static Usuario buscarUsuarioLogado() {
        return (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
