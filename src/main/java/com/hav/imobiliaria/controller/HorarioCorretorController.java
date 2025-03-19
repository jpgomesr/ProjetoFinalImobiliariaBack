package com.hav.imobiliaria.controller;

import com.hav.imobiliaria.controller.dto.agendamento.HorarioCorretorPatchDTO;
import com.hav.imobiliaria.controller.dto.agendamento.HorarioCorretorPostDTO;
import com.hav.imobiliaria.service.HorarioCorretorService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("horarios/corretor")
@AllArgsConstructor
public class HorarioCorretorController {

    private final HorarioCorretorService service;

    @PostMapping
    public ResponseEntity<Void> salvarHorario(@RequestBody @Valid HorarioCorretorPostDTO horarioCorretorPostDTO) {

        this.service.save(horarioCorretorPostDTO);

        return new ResponseEntity<>(HttpStatus.CREATED);

    }
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletarHorario(@PathVariable Long id) {
        this.service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PatchMapping("{id}")
    public ResponseEntity<Void> atualizarHorario(@RequestBody @Valid HorarioCorretorPatchDTO horarioCorretorPatchDTO, @PathVariable Long id) {
        this.service.atualizar(horarioCorretorPatchDTO, id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
