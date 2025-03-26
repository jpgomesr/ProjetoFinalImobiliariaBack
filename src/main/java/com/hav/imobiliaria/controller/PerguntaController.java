package com.hav.imobiliaria.controller;

import com.hav.imobiliaria.controller.dto.pergunta.PerguntaGetDTO;
import com.hav.imobiliaria.controller.dto.pergunta.PerguntaPostDTO;
import com.hav.imobiliaria.controller.mapper.pergunta.PerguntaGetMapper;
import com.hav.imobiliaria.model.entity.Pergunta;
import com.hav.imobiliaria.service.PerguntaService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("FAQs")
@AllArgsConstructor
public class PerguntaController {
    private final PerguntaService service;
    private final PerguntaGetMapper perguntaGetMapper;

    @GetMapping("{id}")
    public ResponseEntity<PerguntaGetDTO> buscarPorId(@PathVariable Long id){
        return ResponseEntity.ok(perguntaGetMapper.toDto(service.buscarPorId(id)));
    }

    @PostMapping
    public ResponseEntity<Pergunta> cadastrar(@RequestBody PerguntaPostDTO perguntaPostDTO){

        return ResponseEntity.ok(service.cadastrar(perguntaPostDTO));
    }




}
