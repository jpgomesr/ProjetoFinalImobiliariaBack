package com.hav.imobiliaria.controller;

import com.hav.imobiliaria.controller.dto.proprietario.ProprietarioDTO;
import com.hav.imobiliaria.controller.mapper.proprietario.ProprietarioMapper;
import com.hav.imobiliaria.model.Proprietario;
import com.hav.imobiliaria.service.ProprietarioService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/proprietarios")
@AllArgsConstructor
public class ProprietarioController implements GenericController {

    private final ProprietarioService service;
    private final ProprietarioMapper proprietarioMapper;

    @GetMapping
    public ResponseEntity<List<Proprietario>> listar() {

        return ResponseEntity.ok(service.findAll());
    }
    @PostMapping
    public ResponseEntity<Proprietario> salvar(@RequestBody ProprietarioDTO proprietarioDTO) {

        Proprietario proprietario = proprietarioMapper.toEntity(proprietarioDTO);

        Proprietario proprietarioSalvo = service.save(proprietario);

        URI uri = gerarHeaderLocation(proprietarioSalvo.getId());

        return ResponseEntity.created(uri).body(proprietarioSalvo);
    }



}
