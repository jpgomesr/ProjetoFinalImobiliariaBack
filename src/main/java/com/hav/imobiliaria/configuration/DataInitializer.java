package com.hav.imobiliaria.configuration;

import com.hav.imobiliaria.model.entity.Administrador;
import com.hav.imobiliaria.model.entity.Corretor;
import com.hav.imobiliaria.model.entity.Editor;
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
        if(usuarioRepository.findByEmail("adm").isEmpty()) {
            Usuario usuario = new Administrador();
            usuario.setEmail("adm");
            usuario.setSenha(passwordEncoder.encode("adm"));
            usuario.setNome("Adm");
            usuario.setAtivo(true);
            usuario.setRole(RoleEnum.ADMINISTRADOR);
            usuarioRepository.save(usuario);
        }
        if(usuarioRepository.findByEmail("usu").isEmpty()) {
            Usuario usuario = new Usuario();
            usuario.setEmail("usu");
            usuario.setSenha(passwordEncoder.encode("usu"));
            usuario.setNome("Usu");
            usuario.setAtivo(true);
            usuario.setRole(RoleEnum.USUARIO);
            usuarioRepository.save(usuario);
        }
        if(usuarioRepository.findByEmail("cor").isEmpty()) {
            Usuario usuario = new Corretor();
            usuario.setEmail("cor");
            usuario.setSenha(passwordEncoder.encode("cor"));
            usuario.setNome("Cor");
            usuario.setAtivo(true);
            usuario.setRole(RoleEnum.CORRETOR);
            usuarioRepository.save(usuario);
        }
        if(usuarioRepository.findByEmail("edi").isEmpty()) {
            Usuario usuario = new Editor();
            usuario.setEmail("edi");
            usuario.setSenha(passwordEncoder.encode("edi"));
            usuario.setNome("Edi");
            usuario.setAtivo(true);
            usuario.setRole(RoleEnum.USUARIO);
            usuarioRepository.save(usuario);
        }


    }

}
