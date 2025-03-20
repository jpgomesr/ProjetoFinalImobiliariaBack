package com.hav.imobiliaria.controller;

import com.hav.imobiliaria.controller.dto.agendamento.AgendamentoListagemDTO;
import com.hav.imobiliaria.controller.dto.agendamento.AgendamentoPostDto;
import com.hav.imobiliaria.controller.dto.agendamento.HorarioCorretorPostDTO;
import com.hav.imobiliaria.service.AgendamentoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/agendamentos")
@AllArgsConstructor
public class AgendamentosController {

    private final AgendamentoService service;


    @PostMapping("horarios")
    public ResponseEntity<Void> agendarHorario(HorarioCorretorPostDTO horarioCorretorPostDTO) {

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @PostMapping
    public ResponseEntity<Void> agendarVisita(@RequestBody AgendamentoPostDto agendamentoPostDto) {
        this.service.salvarAgendamento(agendamentoPostDto);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @GetMapping("/{id}")
    public List<AgendamentoListagemDTO> buscarAgendamentosCorretor(@PathVariable Long id){



    }


}
