package com.hav.imobiliaria.service;

import com.hav.imobiliaria.controller.dto.pergunta.PerguntaPostDTO;
import com.hav.imobiliaria.controller.mapper.pergunta.PerguntaPostMapper;
import com.hav.imobiliaria.model.entity.Pergunta;
import com.hav.imobiliaria.repository.PerguntaRepositry;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

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
}
