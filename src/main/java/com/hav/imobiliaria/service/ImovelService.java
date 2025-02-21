package com.hav.imobiliaria.service;

import com.hav.imobiliaria.model.Imovel;
import com.hav.imobiliaria.repository.ImovelRepository;
import com.hav.imobiliaria.validator.ImovelValidator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ImovelService {



    private final ImovelRepository imovelRepository;
    private final ImovelValidator imovelValidator;


    public Imovel salvar(Imovel imovel) {
        return imovelRepository.save(imovel);
    }

    public ImovelGetDTO buscarPorId(Long id) {
        ImovelGetDTO dto = imovelGetMapper.toDto(repository.findById(id).get());
        return dto;
    }
    public void excluirImovel(Long id) {
        imovelRepository.deleteById(id);
    }
    public Imovel atualizarImovel(Imovel imovel) {
        return imovelRepository.save(imovel);
    }

}
