package com.hav.imobiliaria.configuration;

import com.hav.imobiliaria.model.entity.*;
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
        if(usuarioRepository.findByEmail("a").isEmpty()) {
            Usuario usuario = new Administrador();
            usuario.setEmail("a");
            usuario.setSenha(passwordEncoder.encode("a"));
            usuario.setNome("Adm");
            usuario.setAtivo(true);
            usuario.setRole(RoleEnum.ADMINISTRADOR);
            usuarioRepository.save(usuario);
        }
        if(usuarioRepository.findByEmail("u").isEmpty()) {
            Usuario usuario = new UsuarioComum();
            usuario.setEmail("u");
            usuario.setSenha(passwordEncoder.encode("u"));
            usuario.setNome("Usu");
            usuario.setAtivo(true);
            usuario.setRole(RoleEnum.USUARIO);
            usuarioRepository.save(usuario);
        }
        if(usuarioRepository.findByEmail("c").isEmpty()) {
            Usuario usuario = new Corretor();
            usuario.setEmail("c");
            usuario.setSenha(passwordEncoder.encode("c"));
            usuario.setNome("C");
            usuario.setAtivo(true);
            usuario.setRole(RoleEnum.CORRETOR);
            usuarioRepository.save(usuario);
        }
        if(usuarioRepository.findByEmail("e").isEmpty()) {
            Usuario usuario = new Editor();
            usuario.setEmail("e");
            usuario.setSenha(passwordEncoder.encode("e"));
            usuario.setNome("Edi");
            usuario.setAtivo(true);
            usuario.setRole(RoleEnum.USUARIO);
            usuarioRepository.save(usuario);
        }


    }

}
