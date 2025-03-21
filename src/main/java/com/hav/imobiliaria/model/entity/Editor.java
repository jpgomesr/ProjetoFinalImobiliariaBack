package com.hav.imobiliaria.model.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("EDITOR")
public class Editor extends Usuario {
}
