package com.hav.imobiliaria.controller.mapper.faq;

import com.hav.imobiliaria.controller.dto.faq.FAQGetDTO;
import com.hav.imobiliaria.model.entity.FAQ;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class FAQGetMapper {
    public abstract FAQ toEntity(FAQGetDTO faqGetDTO);

    public abstract FAQGetDTO toDto(FAQ faq);
}
