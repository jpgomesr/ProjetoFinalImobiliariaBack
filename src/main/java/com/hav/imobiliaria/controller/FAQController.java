package com.hav.imobiliaria.controller;

import com.hav.imobiliaria.controller.dto.faq.FAQGetDTO;
import com.hav.imobiliaria.controller.dto.faq.FAQPostDTO;
import com.hav.imobiliaria.controller.mapper.faq.FAQGetMapper;
import com.hav.imobiliaria.controller.mapper.faq.FAQPostMapper;
import com.hav.imobiliaria.model.entity.FAQ;
import com.hav.imobiliaria.service.FAQService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("FAQs")
@AllArgsConstructor
public class FAQController {
    private final FAQService service;
    private final FAQGetMapper faqGetMapper;

    @GetMapping("{id}")
    public ResponseEntity<FAQGetDTO> buscarPorId(@PathVariable Long id){
        return ResponseEntity.ok(faqGetMapper.toDto(service.buscarPorId(id)));
    }

    @PostMapping
    public ResponseEntity<FAQ> cadastrar(@RequestBody FAQPostDTO faqPostDTO){

        return ResponseEntity.ok(service.cadastrar(faqPostDTO));
    }




}
