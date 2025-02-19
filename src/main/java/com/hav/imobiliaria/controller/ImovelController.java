package com.hav.imobiliaria.controller;

import com.hav.imobiliaria.controller.dto.ImovelDTO;
import com.hav.imobiliaria.controller.mapper.ImovelMapper;
import com.hav.imobiliaria.model.Imovel;
import com.hav.imobiliaria.service.ImovelService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("imoveis")
@AllArgsConstructor
public class ImovelController implements GenericController {

    private final ImovelService imovelService;
    private final ImovelMapper imovelMapper;


    @GetMapping
    public ResponseEntity<List<Imovel>> listarImoveis(){


        return ResponseEntity.ok(imovelService.listarImoveis());
    }
    @PostMapping
    public ResponseEntity<Imovel> inserirImovel(@RequestBody ImovelDTO imovelDTO){

        System.out.println(imovelDTO);
        Imovel imovel = imovelMapper.toEntity(imovelDTO);

        return ResponseEntity.ok(imovel);

    }
}
