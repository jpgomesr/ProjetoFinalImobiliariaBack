package com.hav.imobiliaria.controller;

import com.hav.imobiliaria.controller.dto.proprietario.ProprietarioGetDTO;
import com.hav.imobiliaria.controller.dto.proprietario.ProprietarioPostDTO;
import com.hav.imobiliaria.controller.dto.proprietario.ProprietarioPutDTO;
import com.hav.imobiliaria.controller.dto.usuario.UsuarioGetDTO;
import com.hav.imobiliaria.controller.dto.usuario.UsuarioPostDTO;
import com.hav.imobiliaria.controller.dto.usuario.UsuarioPutDTO;
import com.hav.imobiliaria.model.Proprietario;
import com.hav.imobiliaria.service.ProprietarioService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/proprietarios")
@AllArgsConstructor
public class ProprietarioController implements GenericController {

    private final ProprietarioService service;

    @GetMapping
    public ResponseEntity<Page<ProprietarioGetDTO>> listarEmPaginas(Pageable pageable) {
        return ResponseEntity.ok(service.buscarTodos(pageable));
    }

    @GetMapping("{id}")
    public ResponseEntity<ProprietarioGetDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<ProprietarioGetDTO> cadastrar(@RequestBody @Valid ProprietarioPostDTO proprietarioPostDTO) {
        return ResponseEntity.ok(service.salvar(proprietarioPostDTO));
    }

    @PutMapping("{id}")
    public ResponseEntity<ProprietarioGetDTO> atualizar(@RequestBody ProprietarioPutDTO proprietarioPutDTO, @PathVariable Long id) {
        return ResponseEntity.ok(service.atualizar(proprietarioPutDTO,id));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> removerPorId(@PathVariable Long id) {
        service.removerPorId(id);
        return ResponseEntity.noContent().build();
    }
    @PostMapping("/restaurar/{id}")
    public ResponseEntity<Void> restaurarUsuario(@PathVariable Long id) {
        this.service.restaurarUsuario(id);

        return  ResponseEntity.ok().build();
    }





}
