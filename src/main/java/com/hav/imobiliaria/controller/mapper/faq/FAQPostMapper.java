package com.hav.imobiliaria.controller.mapper.faq;

import com.hav.imobiliaria.controller.dto.faq.FAQPostDTO;
import com.hav.imobiliaria.model.entity.FAQ;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class FAQPostMapper {

    public abstract FAQ toEntity(FAQPostDTO faqPostDTO);

    public abstract FAQPostDTO toDto(FAQ faq);
}
