package com.hav.imobiliaria.service;

import com.hav.imobiliaria.model.entity.Corretor;
import com.hav.imobiliaria.model.entity.HorarioCorretor;
import com.hav.imobiliaria.model.entity.Usuario;
import com.hav.imobiliaria.repository.CorretorRepository;
import com.hav.imobiliaria.security.utils.SecurityUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class CorretorService {

    private final CorretorRepository repository;


    public List<HorarioCorretor> buscarHorariosCorretoresPorIdImovel(Long idImovel,Integer mes, Integer dia) {

        return this.repository.findHorariosByImovelAndData(idImovel, mes, dia);

    }
    public List<HorarioCorretor> buscarHorariosCorretoresPorId() {
        Usuario usuario = SecurityUtils.buscarUsuarioLogado();
        return this.repository.findById(usuario.getId()).map(Corretor::getHorarios).orElse(null);
    }

}
