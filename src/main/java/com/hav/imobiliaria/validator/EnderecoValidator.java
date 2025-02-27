package com.hav.imobiliaria.validator;

import com.hav.imobiliaria.exceptions.EnderecoJaCadastradoException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class EnderecoValidator {
    private EnderecoRepository repository;

    public void validar(Endereco endereco){
        if (existeEnderecoCadastrado(endereco)){
            throw new EnderecoJaCadastradoException();
        }
    }

    private boolean existeEnderecoCadastrado(Endereco endereco){
        return repository.existsById(endereco);
    }
}
