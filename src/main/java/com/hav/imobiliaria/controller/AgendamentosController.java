package com.hav.imobiliaria.controller;

import com.hav.imobiliaria.controller.dto.agendamento.AgendamentoListagemDTO;
import com.hav.imobiliaria.controller.dto.agendamento.AgendamentoPostDto;
import com.hav.imobiliaria.controller.dto.agendamento.AgendamentoPutDTO;
import com.hav.imobiliaria.controller.dto.agendamento.HorarioCorretorPostDTO;
import com.hav.imobiliaria.controller.dto.usuario.UsuarioNomeIdDTO;
import com.hav.imobiliaria.controller.mapper.endereco.EnderecoGetMapper;
import com.hav.imobiliaria.model.entity.Agendamento;
import com.hav.imobiliaria.model.enums.StatusAgendamentoEnum;
import com.hav.imobiliaria.service.AgendamentoService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/agendamentos")
@AllArgsConstructor
public class AgendamentosController {

    private final AgendamentoService service;
    private final EnderecoGetMapper enderecoGetMapper;


    @PreAuthorize("hasAnyRole('USUARIO')")
    @PostMapping
    public ResponseEntity<Void> agendarVisita(@RequestBody AgendamentoPostDto agendamentoPostDto) {
        this.service.salvarAgendamento(agendamentoPostDto);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @PreAuthorize("hasAnyRole('USUARIO')")
    @PutMapping
    public ResponseEntity<Void> atualizarVisita(@RequestBody AgendamentoPutDTO agendamentoPutDTO) {
        this.service.atualizarAgendamento(agendamentoPutDTO);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('CORRETOR')")
    @GetMapping("/corretor/{id}")
    public ResponseEntity<Page<AgendamentoListagemDTO>> buscarAgendamentos(
            @PathVariable Long id,
            @RequestParam(required = false, name = "status") StatusAgendamentoEnum status,
            @RequestParam(required = false) LocalDate data,
            Pageable pageable) {

        Page<Agendamento> agendamentos = service.listarAgendamentosCorretorId(
                pageable, id, status, data);

        Page<AgendamentoListagemDTO> agendamentoListagemDTOS = agendamentos.map(agendamento -> {
            return new AgendamentoListagemDTO(
                    agendamento.getId(),
                    agendamento.getDataHora(),
                    enderecoGetMapper.toEnderecoVisitaDTO(agendamento.getImovel().getEndereco()),
                    new UsuarioNomeIdDTO(agendamento.getUsuarioComum().getId(), agendamento.getUsuarioComum().getNome()),
                    new UsuarioNomeIdDTO(agendamento.getCorretor().getId(), agendamento.getCorretor().getNome()),
                    agendamento.getImovel().getId(),
                    agendamento.getImovel().getImagens().getFirst().getReferencia(),
                    agendamento.getStatus());
        });

        return ResponseEntity.ok(agendamentoListagemDTOS);
    }

    @PreAuthorize("hasAnyRole('USUARIO')")
    @GetMapping("/usuario/{id}")
    public ResponseEntity<Page<AgendamentoListagemDTO>> buscarAgendamentosUsuario(
            @PathVariable Long id,
            @RequestParam(required = false, name = "status") StatusAgendamentoEnum status,
            @RequestParam(required = false) LocalDate data,
            Pageable pageable) {

        Page<Agendamento> agendamentos = service.listarAgendamentosUsuarioId(
                pageable, id, status, data);

        Page<AgendamentoListagemDTO> agendamentoListagemDTOS = agendamentos.map(agendamento -> {
            return new AgendamentoListagemDTO(
                    agendamento.getId(),
                    agendamento.getDataHora(),
                    enderecoGetMapper.toEnderecoVisitaDTO(agendamento.getImovel().getEndereco()),
                    new UsuarioNomeIdDTO(agendamento.getUsuarioComum().getId(), agendamento.getUsuarioComum().getNome()),
                    new UsuarioNomeIdDTO(agendamento.getCorretor().getId(), agendamento.getCorretor().getNome()),
                    agendamento.getImovel().getId(),
                    agendamento.getImovel().getImagens().getFirst().getReferencia(),
                    agendamento.getStatus());
        });

        return ResponseEntity.ok(agendamentoListagemDTOS);
    }

    @PreAuthorize("hasAnyRole('CORRETOR','USUARIO')")
    @PatchMapping("{id}")
    public ResponseEntity<Void> alterarStatus(@PathVariable Long id,
                                              @RequestParam StatusAgendamentoEnum status) {

        this.service.atualizarStatus(id, status);

        return ResponseEntity.noContent().build();

    }


}
