package com.hav.imobiliaria.controller;


import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

public interface GenericController {

    default URI gerarHeaderLocation(Long id) {
        return ServletUriComponentsBuilder.fromCurrentRequest().
                path("/{id}").
                buildAndExpand(id).
                toUri();
    }

}
