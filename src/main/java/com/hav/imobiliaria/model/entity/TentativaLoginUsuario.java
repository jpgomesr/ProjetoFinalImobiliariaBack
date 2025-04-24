package com.hav.imobiliaria.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TentativaLoginUsuario {

    @Id
    private String email;

    private Integer tentativas;

    private LocalDateTime ultimaTentativa;


}
