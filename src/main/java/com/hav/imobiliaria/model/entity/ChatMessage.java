package com.hav.imobiliaria.model.entity;

import com.hav.imobiliaria.model.enums.TipoMensagem;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessage {
    private Long id;
    private String conteudo;
    private TipoMensagem tipoMensagem;

}
