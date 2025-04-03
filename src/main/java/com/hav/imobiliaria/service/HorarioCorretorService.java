package com.hav.imobiliaria.service;

import com.hav.imobiliaria.controller.dto.agendamento.HorarioCorretorPatchDTO;
import com.hav.imobiliaria.controller.dto.agendamento.HorarioCorretorPostDTO;
import com.hav.imobiliaria.model.entity.Corretor;
import com.hav.imobiliaria.model.entity.HorarioCorretor;
import com.hav.imobiliaria.model.entity.Usuario;
import com.hav.imobiliaria.repository.HorarioCorretorRepository;
import com.hav.imobiliaria.security.utils.SecurityUtils;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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
    public void delete() {
        Usuario usuario = SecurityUtils.buscarUsuarioLogado();
        repository.deleteById(usuario.getId());

    }


    public void atualizar(HorarioCorretorPatchDTO horarioCorretorPatchDTO) {

        Usuario usuario = SecurityUtils.buscarUsuarioLogado();
        HorarioCorretor horarioCorretor = repository.findById(usuario.getId()).get();
        horarioCorretor.setDataHora(horarioCorretorPatchDTO.horario());
        repository.save(horarioCorretor);
    }



}
