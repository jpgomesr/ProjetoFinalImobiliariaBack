package com.hav.imobiliaria.validator;

import com.hav.imobiliaria.exceptions.campo.CPFJaCadastradoException;
import com.hav.imobiliaria.exceptions.campo.EmailJaCadastradoException;
import com.hav.imobiliaria.exceptions.campo.TelefoneJaCadastradoException;
import com.hav.imobiliaria.model.entity.Proprietario;
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
        if (existeEmailCadastrado(proprietario)){
            throw new EmailJaCadastradoException();
        }
        if(existeCpfJaCadastrado(proprietario)){
            throw new CPFJaCadastradoException();
        }
    }

    private boolean existeEmailCadastrado (Proprietario proprietario){

        Optional<Proprietario> proprietarioOptional = this.repository.findByEmail((proprietario.getEmail()));
        if(proprietario.getId() == null){
            return proprietarioOptional.isPresent();
        }
        return proprietarioOptional.map(Proprietario::getId).
                stream().anyMatch(id -> !id.equals(proprietario.getId()));
    }

    private boolean existeTelefoneCadastrado(Proprietario proprietario){

        Optional<Proprietario> proprietarioOptional = this.repository.findByTelefone(proprietario.getTelefone());
        if(proprietario.getId() == null){
            return proprietarioOptional.isPresent();
        }
        return proprietarioOptional.map(Proprietario::getId).stream()
                .anyMatch(id -> !id.equals(proprietario.getId()));
    }
    private boolean existeCpfJaCadastrado(Proprietario proprietario){
        Optional<Proprietario> proprietarioOptional = this.repository.findByCpf(proprietario.getCpf());
        if(proprietario.getId() == null){
            return proprietarioOptional.isPresent();
        }
        return proprietarioOptional.map(Proprietario::getCpf).stream()
                .anyMatch(cpf -> !cpf.equals(proprietario.getCpf()));

    }

}
