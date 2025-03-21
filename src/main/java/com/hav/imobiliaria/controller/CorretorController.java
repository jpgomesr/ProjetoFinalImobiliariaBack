package com.hav.imobiliaria.controller;

import com.hav.imobiliaria.controller.dto.agendamento.HorarioCorretorGetDTO;
import com.hav.imobiliaria.controller.mapper.agendamento.HorarioCorretorGetMapper;
import com.hav.imobiliaria.model.entity.HorarioCorretor;
import com.hav.imobiliaria.model.entity.Imovel;
import com.hav.imobiliaria.service.CorretorService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("corretores")
@AllArgsConstructor
public class CorretorController {


    private final CorretorService service;
    private final HorarioCorretorGetMapper horarioCorretorGetMapper;

    @GetMapping("/horarios/{id}")
    public ResponseEntity<List<HorarioCorretorGetDTO>> getHorarios(@PathVariable Long id,
                                                                   @RequestParam(required = false) Integer mes,
                                                                   @RequestParam(required = false) Integer dia) {

        return ResponseEntity.ok(this.service.buscarHorariosCorretoresPorIdImovel(id, mes, dia)
                .stream().map(horarioCorretorGetMapper::toDTO).toList());
    }

}
