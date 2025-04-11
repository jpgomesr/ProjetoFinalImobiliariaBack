package com.hav.imobiliaria.controller;

import com.hav.imobiliaria.controller.dto.pergunta.PerguntaGetDTO;
import com.hav.imobiliaria.controller.dto.pergunta.PerguntaPostDTO;
import com.hav.imobiliaria.controller.dto.pergunta.PerguntaRespondidaGetDTO;
import com.hav.imobiliaria.controller.dto.pergunta.PerguntaRespondidaPatchDTO;
import com.hav.imobiliaria.controller.dto.pergunta.PerguntaRespondidaPatchDTO;
import com.hav.imobiliaria.controller.mapper.pergunta.PerguntaGetMapper;
import com.hav.imobiliaria.controller.mapper.pergunta.PerguntaPatchMapper;
import com.hav.imobiliaria.controller.mapper.pergunta.PerguntaRespondidaGetMapper;
import com.hav.imobiliaria.model.entity.Pergunta;
import com.hav.imobiliaria.model.enums.TipoPerguntaEnum;
import com.hav.imobiliaria.service.PerguntaService;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("perguntas")
@AllArgsConstructor
public class PerguntaController {
    private final PerguntaService service;
    private final PerguntaGetMapper perguntaGetMapper;
    private final PerguntaPatchMapper perguntaPatchMapper;
    private final PerguntaRespondidaGetMapper perguntaRespondidaGetMapper;

    @GetMapping("{id}")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'EDITOR')")
    public ResponseEntity<PerguntaGetDTO> buscarPorId(@PathVariable Long id){
        return ResponseEntity.ok(perguntaGetMapper.toDto(service.buscarPorId(id)));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'EDITOR')")
    public ResponseEntity<Page<PerguntaGetDTO>> buscarTodasPerguntas(
            @RequestParam(value = "tipo_pergunta") @Nullable TipoPerguntaEnum tipoPergunta,
            @RequestParam(value = "email") @Nullable String email,
            @RequestParam(value = "titulo") @Nullable String titulo,
            @RequestParam(value = "mensagem") @Nullable String mensagem,
            Pageable pageable
    ){
        Page<Pergunta> paginaResultadoDTO = service.pesquisar(tipoPergunta, email, titulo, mensagem, pageable);
        return ResponseEntity.ok(paginaResultadoDTO.map(perguntaGetMapper::toDto));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'EDITOR')")
    public ResponseEntity<Pergunta> cadastrar(@RequestBody PerguntaPostDTO perguntaPostDTO){
        return ResponseEntity.ok(service.cadastrar(perguntaPostDTO));
    }

    @PatchMapping("{id}")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'EDITOR')")
    private ResponseEntity<PerguntaRespondidaGetDTO> responder(
            @PathVariable Long id,
            @RequestParam String resposta){
        Pergunta atualizacaoPergunta = service.responder(resposta, id);
        PerguntaRespondidaGetDTO getDto = perguntaPatchMapper.toGetDto(atualizacaoPergunta);

        return ResponseEntity.ok(getDto);
    }






}
