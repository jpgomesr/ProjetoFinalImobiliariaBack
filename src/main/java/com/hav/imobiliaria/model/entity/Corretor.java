package com.hav.imobiliaria.model.entity;

import com.hav.imobiliaria.exceptions.requisicao_padrao.HorarioCorretorInvalidoException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@DiscriminatorValue("CORRETOR")
@Getter
@Setter
public class Corretor extends Usuario{

    @ManyToMany(mappedBy = "corretores")
    private List<Imovel> imoveis;

    @OneToMany(mappedBy = "corretor", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<HorarioCorretor> horarios;

    @OneToMany(mappedBy = "corretor", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Agendamento> agendamentos;


    public void removerHorarioPorDatahora(LocalDateTime datahora) {

        for(int i = 0 ; i < horarios.size() ; i++){
            if(horarios.get(i).getDataHora().equals(datahora)){
                horarios.remove(i);
                return;
            }
        }
        throw new HorarioCorretorInvalidoException("O horario informado é inválido");
    }


}
