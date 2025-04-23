package com.hav.imobiliaria.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class TentativaLoginUsuario {

    @Id
    private String email;

    private Integer tentativas;

    private LocalDateTime ultimaTentativa;

}
