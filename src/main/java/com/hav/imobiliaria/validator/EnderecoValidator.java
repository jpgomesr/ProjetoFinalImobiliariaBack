package com.hav.imobiliaria.validator;

import com.hav.imobiliaria.exceptions.EnderecoJaCadastradoException;
import com.hav.imobiliaria.model.Endereco;
import com.hav.imobiliaria.repository.EnderecoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class EnderecoValidator {
    private EnderecoRepository repository;

    public void validar(Endereco endereco){
        
    }
}
