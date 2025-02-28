package com.hav.imobiliaria.validator;

import com.hav.imobiliaria.exceptions.ImovelJaCadastradoException;
import com.hav.imobiliaria.model.Imovel;
import com.hav.imobiliaria.model.Proprietario;
import com.hav.imobiliaria.repository.ImovelRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class ImovelValidator {
    private ImovelRepository repository;

    public void validar(Imovel imovel){
    }


}
