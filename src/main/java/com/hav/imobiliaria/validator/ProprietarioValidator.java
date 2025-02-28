package com.hav.imobiliaria.validator;

import com.hav.imobiliaria.exceptions.UsuarioJaCadastradoException;
import com.hav.imobiliaria.model.Proprietario;
import com.hav.imobiliaria.repository.ProprietarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ProprietarioValidator {
    private ProprietarioRepository repository;

    public void validar(Proprietario proprietario){
        if (existeProprietarioCadastrado(proprietario)){
            throw new UsuarioJaCadastradoException();
        }
    }

    private boolean existeProprietarioCadastrado(Proprietario proprietario){
        return repository.existsById(proprietario.getId());
    }
}
