package com.hav.imobiliaria.model.entity;

import com.hav.imobiliaria.model.enums.StatusAgendamentoEnum;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "agendamento")
@Data
public class Agendamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private LocalDateTime dataHora;

    @ManyToOne
    @JoinColumn(name = "id_imovel")
    private Imovel imovel;

    @ManyToOne
    @JoinColumn(name = "id_corretor")
    private Corretor corretor;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private UsuarioComum usuarioComum;


    private StatusAgendamentoEnum status;


    @PrePersist
    public void prePersist(){
        if(status == null){
            status = StatusAgendamentoEnum.PENDENTE;
        }
    }
}
