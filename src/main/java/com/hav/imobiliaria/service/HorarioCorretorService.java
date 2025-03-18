package com.hav.imobiliaria.service;

import com.hav.imobiliaria.controller.dto.agendamento.HorarioCorretorPatchDTO;
import com.hav.imobiliaria.controller.dto.agendamento.HorarioCorretorPostDTO;
import com.hav.imobiliaria.model.entity.HorarioCorretor;
import com.hav.imobiliaria.repository.HorarioCorretorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class HorarioCorretorService {

    private final HorarioCorretorRepository repository;
    private final UsuarioService usuarioService;

    public void save(HorarioCorretorPostDTO horarioCorretorDTO) {
        HorarioCorretor horarioCorretorEntity = new HorarioCorretor();
        horarioCorretorEntity.setCorretor(usuarioService.buscarCorretor(horarioCorretorDTO.idCorretor()));
        horarioCorretorEntity.setDataHora(horarioCorretorDTO.horario());

        repository.save(horarioCorretorEntity);
    }
    public void delete(Long id) {
        repository.deleteById(id);
    }
    public void atualizar(HorarioCorretorPatchDTO horarioCorretorPatchDTO, Long id) {
        HorarioCorretor horarioCorretor = repository.findById(id).get();
        horarioCorretor.setDataHora(horarioCorretorPatchDTO.horario());
        repository.save(horarioCorretor);
    }


}
