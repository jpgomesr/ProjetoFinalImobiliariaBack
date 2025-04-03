package com.hav.imobiliaria.controller;

import com.hav.imobiliaria.repository.EnderecoRepository;
import com.hav.imobiliaria.service.EnderecoService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("enderecos")
@AllArgsConstructor
public class EnderecoController {

    private final EnderecoService service;

    @PreAuthorize("permitAll()")
    @GetMapping("cidades/{estado}")
    public ResponseEntity<Set<String>>buscarCidades(@PathVariable  String estado){
        String estadoFormatado = estado.replace("-"," ");
        System.out.println(service.buscarCidades(estadoFormatado));

        return ResponseEntity.ok(service.buscarCidades(estadoFormatado));

    }
    @PreAuthorize("permitAll()")
    @GetMapping("bairros/{cidade}")
    private ResponseEntity<Set<String>>buscarBairros(@PathVariable String cidade){
        String cidadeFormatada = cidade.replace("-"," ");

        return ResponseEntity.ok(service.buscarBarrosPorCidade(cidadeFormatada));
    }


}
