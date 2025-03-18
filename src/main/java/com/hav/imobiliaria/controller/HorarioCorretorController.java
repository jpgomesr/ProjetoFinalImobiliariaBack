package com.hav.imobiliaria.controller;

import com.hav.imobiliaria.service.HorarioCorretorService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("horarios/corretor")
@AllArgsConstructor
public class HorarioCorretorController {

    private final HorarioCorretorService service;

 
}
