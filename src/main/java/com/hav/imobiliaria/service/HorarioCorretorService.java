package com.hav.imobiliaria.service;

import com.hav.imobiliaria.controller.dto.agendamento.HorarioCorretorPatchDTO;
import com.hav.imobiliaria.controller.dto.agendamento.HorarioCorretorPostDTO;
import com.hav.imobiliaria.model.entity.Corretor;
import com.hav.imobiliaria.model.entity.HorarioCorretor;
import com.hav.imobiliaria.model.entity.Usuario;
import com.hav.imobiliaria.repository.HorarioCorretorRepository;
import com.hav.imobiliaria.security.utils.SecurityUtils;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
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

    @Transactional
    public void delete(Long id) {
        Long  idUsuario = SecurityUtils.buscarUsuarioLogado().getId();
        Corretor corretor =  usuarioService.buscarCorretor(idUsuario);

        corretor.getHorarios().removeIf(horario -> horario.getId().equals(id));


    }


    public void atualizar(HorarioCorretorPatchDTO horarioCorretorPatchDTO) {

        Usuario usuario = SecurityUtils.buscarUsuarioLogado();
        HorarioCorretor horarioCorretor = repository.findById(usuario.getId()).get();
        horarioCorretor.setDataHora(horarioCorretorPatchDTO.horario());
        repository.save(horarioCorretor);
    }



}
