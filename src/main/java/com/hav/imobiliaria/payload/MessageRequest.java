package com.hav.imobiliaria.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageRequest {
    private String conteudo;
    private String remetente;
    private Long idChat;
    private LocalDateTime timestamp;
    private String nomeRemetente;
}
