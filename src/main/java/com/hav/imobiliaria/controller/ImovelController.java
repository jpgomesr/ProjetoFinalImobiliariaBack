package com.hav.imobiliaria.controller;

import com.hav.imobiliaria.controller.dto.imovel.ImovelGetDTO;
import com.hav.imobiliaria.controller.dto.imovel.ImovelPostDTO;
import com.hav.imobiliaria.controller.dto.imovel.ImovelPutDTO;
import com.hav.imobiliaria.controller.mapper.imovel.ImovelGetMapper;
import com.hav.imobiliaria.controller.mapper.imovel.ImovelPostMapper;
import com.hav.imobiliaria.controller.mapper.imovel.ImovelPostMapper;
import com.hav.imobiliaria.controller.mapper.imovel.ImovelPutMapper;
import com.hav.imobiliaria.model.Imovel;
import com.hav.imobiliaria.model.TipoFinalidadeEnum;
import com.hav.imobiliaria.service.ImovelService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("imoveis")
@AllArgsConstructor
public class ImovelController implements GenericController {
    private final ImovelService service;

    @GetMapping
    public ResponseEntity<Page<ImovelGetDTO>> listarImoveis(
            @RequestParam(value = "descricao", required = false) String descricao,
            @RequestParam(value = "tamanho", required = false) Integer tamanho,
            @RequestParam(value = "titulo", required = false) String titulo,
            @RequestParam(value = "tipoResidencia", required = false) String tipoResidencia,
            @RequestParam(value = "qtdQuartos", required = false) Integer qtdQuartos,
            @RequestParam(value = "qtdBanheiros", required = false) Integer qtdBanheiros,
            @RequestParam(value = "qtdGaragens", required = false) Integer qtdGaragens,
            @RequestParam(value = "precoMin", required = false) Double precoMin,
            @RequestParam(value = "precoMax", required = false) Double precoMax,
            @RequestParam(value = "finalidade", required = false) TipoFinalidadeEnum finalidade,
            @RequestParam(value = "pagina", defaultValue = "0") Integer pagina,
            @RequestParam(value = "tamanho-pagina", defaultValue = "10") Integer tamanhoPagina
    ) {


        Page<ImovelGetDTO> paginaResultadoDto = service.pesquisa(descricao,tamanho, titulo, tipoResidencia, qtdBanheiros, qtdQuartos,
                qtdGaragens, precoMin, precoMax, finalidade, pagina, tamanhoPagina);


        return ResponseEntity.ok(paginaResultadoDto);
    }

    @GetMapping("{id}")
    public ResponseEntity<ImovelGetDTO> buscarPorId(@PathVariable Long id){
        return ResponseEntity.ok(service.buscarPorId(id));
    }
    @PostMapping
    public ResponseEntity<ImovelGetDTO> cadastrar(@RequestBody ImovelPostDTO imovelPostDTO) {
        return ResponseEntity.ok(service.salvar(imovelPostDTO));
    }
    @PutMapping("{id}")
    public ResponseEntity<ImovelGetDTO> atualizar(@RequestBody ImovelPutDTO imovelPutDTO, @PathVariable Long id){
        return ResponseEntity.ok(service.atualizar(imovelPutDTO,id));
    }
    @DeleteMapping("{id}")
    public ResponseEntity<Void> removerPorId(@PathVariable Long id){
        service.removerPorId(id);
        return ResponseEntity.noContent().build();
    }
}
