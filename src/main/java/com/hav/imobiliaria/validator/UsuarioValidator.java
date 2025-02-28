package com.hav.imobiliaria.validator;

import com.hav.imobiliaria.exceptions.UsuarioJaCadastradoException;
import com.hav.imobiliaria.model.Usuario;
import com.hav.imobiliaria.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UsuarioValidator {
    private UsuarioRepository repository;

    public void validar(Usuario usuario){
        if (existeUsuarioCadastrado(usuario)){
            throw new UsuarioJaCadastradoException();
        }
    }

    private boolean existeUsuarioCadastrado(Usuario usuario){
        return repository.existsById(usuario.getId());
    }


}
