package com.hav.imobiliaria.model.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "horario_corretor")
@Data
public class HorarioCorretor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private LocalDateTime dataHora;

    @ManyToOne
    @JoinColumn
    private Corretor corretor;
}
