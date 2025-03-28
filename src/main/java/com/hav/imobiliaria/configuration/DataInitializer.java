package com.hav.imobiliaria.configuration;

import com.hav.imobiliaria.model.entity.Administrador;
import com.hav.imobiliaria.model.entity.Usuario;
import com.hav.imobiliaria.model.enums.RoleEnum;
import com.hav.imobiliaria.repository.UsuarioRepository;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DataInitializer {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {
        if(usuarioRepository.findByEmail("adm@adm.com").isEmpty()) {
            Usuario usuario = new Administrador();
            usuario.setEmail("adm@adm.com");
            usuario.setSenha(passwordEncoder.encode("adm"));
            usuario.setNome("Adm");
            usuario.setAtivo(true);
            usuario.setRole(RoleEnum.ADMINISTRADOR);
            usuarioRepository.save(usuario);
        }

    }

}
