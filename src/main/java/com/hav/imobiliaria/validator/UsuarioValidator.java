package com.hav.imobiliaria.validator;

import com.hav.imobiliaria.exceptions.EmailJaCadastradoException;
import com.hav.imobiliaria.exceptions.TelefoneJaCadastradoException;
import com.hav.imobiliaria.exceptions.UsuarioJaCadastradoException;
import com.hav.imobiliaria.model.Usuario;
import com.hav.imobiliaria.repository.UsuarioRepository;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.util.Optional;

@Component
@AllArgsConstructor
public class UsuarioValidator {
    private UsuarioRepository repository;

    public void validar(Usuario usuario){
        if (existeEmailCadastrado(usuario)){
            throw new EmailJaCadastradoException();
        }
        if (existeTelefoneCadastrado(usuario)){
            throw new TelefoneJaCadastradoException();
        }
        emailInvalido(usuario.getEmail());
    }

    private void emailInvalido(@Email(message = "Email inválido") String email){
    }

    private boolean existeEmailCadastrado(Usuario usuario){

        Optional<Usuario> usuario1 = this.repository.findByEmail(usuario.getEmail());
        return usuario1.isEmpty() || usuario1.get().getId().equals(usuario.getId());
    }

    private boolean existeTelefoneCadastrado(Usuario usuario){
        Optional<Usuario> usuario1 = this.repository.findByTelefone(usuario.getTelefone());
        return usuario1.isEmpty() || usuario1.get().getId().equals(usuario.getId());
    }
}