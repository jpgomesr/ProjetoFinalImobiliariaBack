package com.hav.imobiliaria.controller;

import com.hav.imobiliaria.controller.dto.pergunta.PerguntaGetDTO;
import com.hav.imobiliaria.controller.dto.pergunta.PerguntaPostDTO;
import com.hav.imobiliaria.controller.mapper.pergunta.PerguntaGetMapper;
import com.hav.imobiliaria.model.entity.Pergunta;
import com.hav.imobiliaria.model.enums.TipoPerguntaEnum;
import com.hav.imobiliaria.service.PerguntaService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("perguntas")
@AllArgsConstructor
public class PerguntaController {
    private final PerguntaService service;
    private final PerguntaGetMapper perguntaGetMapper;

    @GetMapping("{id}")
    public ResponseEntity<PerguntaGetDTO> buscarPorId(@PathVariable Long id){
        return ResponseEntity.ok(perguntaGetMapper.toDto(service.buscarPorId(id)));
    }

    @GetMapping
    public ResponseEntity<Page<PerguntaGetDTO>> buscarTodasPerguntas(
            @RequestParam(value = "tipo_pergunta") TipoPerguntaEnum tipoPergunta,
            @RequestParam(value = "email") String email,
            @RequestParam(value = "telefone") String telefone,
            @RequestParam(value = "nome") String nome,
            @RequestParam(value = "mensagem") String mensagem,
            Pageable pageable
    ){
        Page<Pergunta> paginaResultadoDTO = service.pesquisar(tipoPergunta, email, telefone, nome, mensagem, pageable);
        return ResponseEntity.ok(paginaResultadoDTO.map(perguntaGetMapper::toDto));
    }

    @PostMapping
    public ResponseEntity<Pergunta> cadastrar(@RequestBody PerguntaPostDTO perguntaPostDTO){
        return ResponseEntity.ok(service.cadastrar(perguntaPostDTO));
    }






}
