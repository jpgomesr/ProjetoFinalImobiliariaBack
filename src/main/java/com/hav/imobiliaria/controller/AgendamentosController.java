package com.hav.imobiliaria.controller;

import com.hav.imobiliaria.controller.dto.agendamento.HorarioCorretorPostDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/agendamentos")
public class AgendamentosController {

    @PostMapping("horarios")
    public ResponseEntity<Void> agendarHorario(HorarioCorretorPostDTO horarioCorretorPostDTO) {


        return new ResponseEntity<>(HttpStatus.CREATED);

    }

}
