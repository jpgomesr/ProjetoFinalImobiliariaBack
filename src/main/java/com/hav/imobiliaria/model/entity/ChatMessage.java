package com.hav.imobiliaria.model.entity;

import com.hav.imobiliaria.model.enums.TipoMensagem;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatMessage {
    private String conteudo;
    private String remetente;
    private TipoMensagem tipoMensagem;

}
