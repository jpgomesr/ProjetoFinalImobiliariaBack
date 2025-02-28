package com.hav.imobiliaria.validator;

import com.hav.imobiliaria.exceptions.CPFJaCadastradoException;
import com.hav.imobiliaria.exceptions.TelefoneJaCadastradoException;
import com.hav.imobiliaria.exceptions.UsuarioJaCadastradoException;
import com.hav.imobiliaria.model.Proprietario;
import com.hav.imobiliaria.repository.ProprietarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class ProprietarioValidator {
    private ProprietarioRepository repository;

    public void validar(Proprietario proprietario){
        if (existeTelefoneCadastrado(proprietario)){
            throw new TelefoneJaCadastradoException();
        }
        if (existeCPFCadastrado(proprietario)){
            throw new CPFJaCadastradoException();
        }
    }

    private boolean existeTelefoneCadastrado(Proprietario proprietario){

        Optional<Proprietario> proprietario1 = this.repository.findByTelefone(proprietario.getTelefone());
        return proprietario1.isEmpty() || !proprietario1.get().getId().equals(proprietario.getId());
    }

    private boolean existeCPFCadastrado(Proprietario proprietario){

        Optional<Proprietario> proprietario1 = this.repository.findByCpf(proprietario.getCpf());
        return proprietario1.isEmpty() || !proprietario1.get().getId().equals(proprietario.getId());
    }

}
