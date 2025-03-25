package com.hav.imobiliaria.service;

import com.hav.imobiliaria.controller.dto.faq.FAQPostDTO;
import com.hav.imobiliaria.controller.mapper.faq.FAQPostMapper;
import com.hav.imobiliaria.model.entity.FAQ;
import com.hav.imobiliaria.repository.FAQRepositry;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class FAQService {
    private final FAQRepositry repositry;

    private final FAQPostMapper faqPostMapper;

    public FAQ buscarPorId(Long id){
        return repositry.findById(id).get();
    }

    public FAQ cadastrar(FAQPostDTO faq){
        var entity = faqPostMapper.toEntity(faq);

        System.out.println(entity);
        return repositry.save(entity);
    }
}
