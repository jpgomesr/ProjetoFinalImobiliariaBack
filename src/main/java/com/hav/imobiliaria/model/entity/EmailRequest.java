package com.hav.imobiliaria.model.entity;

import com.hav.imobiliaria.model.enums.TipoEmailEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmailRequest {
    private String destinatario;
    private TipoEmailEnum tipoEmail;
    private Map<String, Object> variaveis;
}
