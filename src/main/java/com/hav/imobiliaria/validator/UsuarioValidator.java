package com.hav.imobiliaria.validator;

import com.hav.imobiliaria.exceptions.campo.EmailJaCadastradoException;
import com.hav.imobiliaria.model.entity.Usuario;
import com.hav.imobiliaria.repository.UsuarioRepository;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class UsuarioValidator {
    private UsuarioRepository repository;

    public void validar(Usuario usuario){
        if (existeEmailCadastrado(usuario)){
            throw new EmailJaCadastradoException();
        }
//        if (existeTelefoneCadastrado(usuario)){
//            throw new TelefoneJaCadastradoException();
//        }
        emailInvalido(usuario.getEmail());
    }

    private void emailInvalido(@Email(message = "Email inválido") String email){
    }

    private boolean existeEmailCadastrado(Usuario usuario){

        Optional<Usuario> usuarioOptional = this.repository.findByEmail(usuario.getEmail());
        if(usuario.getId() == null){
            return usuarioOptional.isPresent();
        }
        return usuarioOptional.map(Usuario::getId).
                stream().anyMatch(id -> !id.equals(usuario.getId()));
    }

//    private boolean existeTelefoneCadastrado(Usuario usuario){
//        Optional<Usuario> usuarioOptional = this.repository.findByTelefone(usuario.getTelefone());
//        if(usuario.getId() == null){
//            return usuarioOptional.isPresent();
//        }
//        return usuarioOptional.map(Usuario::getId).stream()
//                .anyMatch(id -> !id.equals(usuario.getId()));
//    }
}