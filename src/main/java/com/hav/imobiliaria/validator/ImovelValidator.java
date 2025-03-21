package com.hav.imobiliaria.validator;

import com.hav.imobiliaria.model.entity.Imovel;
import com.hav.imobiliaria.repository.ImovelRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ImovelValidator {
    private ImovelRepository repository;

    public void validar(Imovel imovel){

    }


}
