package com.hav.imobiliaria.controller;


import com.hav.imobiliaria.controller.dto.endereco.EnderecoGetDTO;
import com.hav.imobiliaria.controller.dto.endereco.EnderecoPostDTO;
import com.hav.imobiliaria.controller.dto.endereco.EnderecoPutDTO;
import com.hav.imobiliaria.repository.EnderecoRepository;
import com.hav.imobiliaria.service.EnderecoService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("enderecos")
@AllArgsConstructor
public class EnderecoController implements GenericController {

    private final EnderecoService service;

    @GetMapping
    public ResponseEntity<Page<EnderecoGetDTO>> listarEnderecos(Pageable pageable) {
        return ResponseEntity.ok(service.buscarTodos(pageable));
    }

    @GetMapping("{id}")
    public ResponseEntity<EnderecoGetDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<EnderecoGetDTO> cadastrar(@RequestBody EnderecoPostDTO enderecoPostDTO) {
        return ResponseEntity.ok(service.salvar(enderecoPostDTO));
    }

    @PutMapping
    public ResponseEntity<EnderecoGetDTO> atualizar(@RequestBody EnderecoPutDTO enderecoPutDTO, @PathVariable Long id) {
        return ResponseEntity.ok(service.atualizar(enderecoPutDTO, id));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        service.removerPorId(id);
        return ResponseEntity.noContent().build();
    }
}

