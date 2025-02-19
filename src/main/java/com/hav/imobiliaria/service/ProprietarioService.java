package com.hav.imobiliaria.service;

import com.hav.imobiliaria.model.Proprietario;
import com.hav.imobiliaria.repository.ProprietarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProprietarioService {

    private final ProprietarioRepository repository;

    public List<Proprietario> findAll() {
        return repository.findAll();
    }
    public Proprietario findById(Long id) {
        return repository.findById(id).get();
    }
    public Proprietario save(Proprietario proprietario) {
        return repository.save(proprietario);
    }
    public void delete(Proprietario proprietario) {
        repository.delete(proprietario);
    }
    public Proprietario atualizar(Proprietario proprietario) {
        return repository.save(proprietario);
    }


}
