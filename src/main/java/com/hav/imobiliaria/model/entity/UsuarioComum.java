package com.hav.imobiliaria.model.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@DiscriminatorValue("USUARIO")
public class UsuarioComum extends Usuario {

    @OneToMany(mappedBy = "usuarioComum")
    List<Agendamento> agendamentos;



}

