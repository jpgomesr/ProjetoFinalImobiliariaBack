package com.hav.imobiliaria.validator;

import com.hav.imobiliaria.exceptions.ImovelJaCadastrado;
import com.hav.imobiliaria.model.Imovel;
import com.hav.imobiliaria.repository.ImovelRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ImovelValidator {
    private ImovelRepository repository;

    public void validar(Imovel imovel){
        if (existeImovelCadastrado(imovel)){
            throw new ImovelJaCadastrado();
        }
    }

    private boolean existeImovelCadastrado(Imovel imovel){
        return repository.existsById(imovel.getId());
    }
}
