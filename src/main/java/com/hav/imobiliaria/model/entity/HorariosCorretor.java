package com.hav.imobiliaria.model.entity;


import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "horarios_corretor")
public class HorariosCorretor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private LocalDateTime dataHora;
}
