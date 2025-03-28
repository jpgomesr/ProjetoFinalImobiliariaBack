package com.hav.imobiliaria.service;

import com.hav.imobiliaria.controller.dto.pergunta.PerguntaPostDTO;
import com.hav.imobiliaria.controller.mapper.pergunta.PerguntaPostMapper;
import com.hav.imobiliaria.model.entity.Pergunta;
import com.hav.imobiliaria.model.enums.TipoPerguntaEnum;
import com.hav.imobiliaria.repository.PerguntaRepositry;
import com.hav.imobiliaria.repository.specs.ImovelSpecs;
import com.hav.imobiliaria.repository.specs.PerguntaSpecs;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

@Service
@AllArgsConstructor
public class PerguntaService {
    private final PerguntaRepositry repositry;

    private final PerguntaPostMapper perguntaPostMapper;

    public Pergunta buscarPorId(Long id){
        return repositry.findById(id).get();
    }

    public Pergunta cadastrar(PerguntaPostDTO perguntaPostDTO){
        var entity = perguntaPostMapper.toEntity(perguntaPostDTO);

        System.out.println(entity);
        return repositry.save(entity);
    }

    public Page<Pergunta> pesquisar(
            TipoPerguntaEnum tipoPergunta,
            String email,
            String telefone,
            String nome,
            String mensagem,
            Pageable pageable
    ) {
        Specification<Pergunta> specs = Specification.where((root, query, criteriaBuilder) -> criteriaBuilder.conjunction());

        telefone = telefone.replace("(", "")
                .replace(")", "").replace("-","");

        if (tipoPergunta !=null){
            specs = specs.and(PerguntaSpecs.tipoPerguntaLike(tipoPergunta));
        }
        if (StringUtils.isNotBlank(email)){
            specs = specs.and(PerguntaSpecs.emailLike(email));
        }
        if (StringUtils.isNotBlank(nome)){
            specs = specs.and(PerguntaSpecs.nomeLike(nome));
        }
        if (StringUtils.isNotBlank(telefone)){
            specs = specs.and(PerguntaSpecs.mensagemLike(mensagem));
        }
        return repositry.findAll(specs, pageable);
    }
}
