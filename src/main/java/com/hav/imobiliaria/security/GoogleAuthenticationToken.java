package com.hav.imobiliaria.security;

import com.hav.imobiliaria.model.entity.Usuario;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class GoogleAuthenticationToken extends AbstractAuthenticationToken {


    private final Usuario principal;

    public GoogleAuthenticationToken(Collection<? extends GrantedAuthority> authorities, Usuario principal) {
        super(authorities);
        this.principal = principal;
        setAuthenticated(false);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }
}
