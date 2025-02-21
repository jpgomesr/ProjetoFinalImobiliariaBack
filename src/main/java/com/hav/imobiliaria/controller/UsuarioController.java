package com.hav.imobiliaria.controller;

import com.hav.imobiliaria.controller.dto.usuario.UsuarioGetDTO;
import com.hav.imobiliaria.controller.dto.usuario.UsuarioPostDTO;
import com.hav.imobiliaria.controller.dto.usuario.UsuarioPutDTO;
import com.hav.imobiliaria.service.UsuarioService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
@AllArgsConstructor
public class UsuarioController implements GenericController{

    private UsuarioService service;

    @GetMapping
    public ResponseEntity<Page<UsuarioGetDTO>> listarEmPaginas(Pageable pageable) {
        return ResponseEntity.ok(service.buscarTodos(pageable));
    }
    @GetMapping("{id}")
    public ResponseEntity<UsuarioGetDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }
    @PostMapping
    public ResponseEntity<UsuarioGetDTO> cadastrar(@RequestBody UsuarioPostDTO usuarioPostDTO) {
        return ResponseEntity.ok(service.salvar(usuarioPostDTO));
    }
    @PutMapping("{id}")
    public ResponseEntity<UsuarioGetDTO> atualizar(@RequestBody UsuarioPutDTO usuarioPutDTO, @PathVariable Long id) {
        return ResponseEntity.ok(service.atualizar(usuarioPutDTO,id));
    }
    @DeleteMapping("{id}")
    public ResponseEntity<Void> removerPorId(@PathVariable Long id) {
        service.removerPorId(id);
        return ResponseEntity.noContent().build();
    }
}
