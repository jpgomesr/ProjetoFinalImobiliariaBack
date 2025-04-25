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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Perguntas", description = "Operações relacionadas a perguntas dos clientes")
public class PerguntaController {
    private final PerguntaService service;
    private final PerguntaGetMapper perguntaGetMapper;
    private final PerguntaPatchMapper perguntaPatchMapper;
    private final PerguntaRespondidaGetMapper perguntaRespondidaGetMapper;

    @GetMapping("{id}")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'EDITOR')")
    @Operation(summary = "Buscar pergunta por ID", description = "Retorna os detalhes de uma pergunta específica")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pergunta encontrada com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso negado", content = @Content),
        @ApiResponse(responseCode = "404", description = "Pergunta não encontrada", content = @Content)
    })
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<PerguntaGetDTO> buscarPorId(
            @Parameter(description = "ID da pergunta", required = true) 
            @PathVariable Long id) {
        return ResponseEntity.ok(perguntaGetMapper.toDto(service.buscarPorId(id)));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'EDITOR')")
    @Operation(summary = "Listar perguntas", description = "Retorna uma lista paginada de perguntas com base nos filtros fornecidos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Perguntas encontradas com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso negado", content = @Content),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = @Content)
    })
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<Page<PerguntaGetDTO>> buscarPerguntasNaoRespondidas(
            @Parameter(description = "Tipo de pergunta") 
            @RequestParam(value = "tipo_pergunta") @Nullable TipoPerguntaEnum tipoPergunta,
            @Parameter(description = "Email do remetente") 
            @RequestParam(value = "email") @Nullable String email,
            @Parameter(description = "Título da pergunta") 
            @RequestParam(value = "titulo") @Nullable String titulo,
            @Parameter(description = "Conteúdo da mensagem") 
            @RequestParam(value = "mensagem") @Nullable String mensagem,
            @Parameter(description = "Filtrar por perguntas já respondidas") 
            @RequestParam(value = "buscarRespondida", required = false) @Nullable Boolean buscarRespondida,
            @Parameter(description = "Configuração de paginação") 
            Pageable pageable
    ) {
        Page<Pergunta> paginaResultadoDTO =
                service.pesquisar(tipoPergunta, email, titulo, mensagem, buscarRespondida,pageable);
        return ResponseEntity.ok(paginaResultadoDTO.map(perguntaGetMapper::toDto));
    }


    @PostMapping
    @PreAuthorize("permitAll()")
    @Operation(summary = "Cadastrar pergunta", description = "Cadastra uma nova pergunta no sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pergunta cadastrada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content)
    })
    public ResponseEntity<Pergunta> cadastrar(
            @Parameter(description = "Dados da pergunta", required = true) 
            @RequestBody PerguntaPostDTO perguntaPostDTO) {
        return ResponseEntity.ok(service.cadastrar(perguntaPostDTO));
    }

    @PatchMapping("{id}")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'EDITOR')")
    @Operation(summary = "Responder pergunta", description = "Adiciona uma resposta a uma pergunta existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pergunta respondida com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content),
        @ApiResponse(responseCode = "403", description = "Acesso negado", content = @Content),
        @ApiResponse(responseCode = "404", description = "Pergunta não encontrada", content = @Content)
    })
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<PerguntaRespondidaGetDTO> responder(
            @Parameter(description = "ID da pergunta", required = true) 
            @PathVariable Long id,
            @Parameter(description = "Conteúdo da resposta", required = true) 
            @RequestParam String resposta) {
        Pergunta atualizacaoPergunta = service.responder(resposta, id);
        PerguntaRespondidaGetDTO getDto = perguntaPatchMapper.toGetDto(atualizacaoPergunta);

        return ResponseEntity.ok(getDto);
    }
}
