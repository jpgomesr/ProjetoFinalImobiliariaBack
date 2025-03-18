package com.hav.imobiliaria.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@DiscriminatorValue("CORRETOR")
@Getter
@Setter
public class Corretor extends Usuario{

    @ManyToMany(mappedBy = "corretores")
    // Refere-se ao atributo 'corretores' na classe Imovel
    private List<Imovel> imoveis;

    @OneToMany(mappedBy = "corretor")
    private List<HorarioCorretor> horariosCorretor;

    @OneToMany(mappedBy = "corretor")
    private List<Agendamento> agendamentos;

}
