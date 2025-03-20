package com.hav.imobiliaria.controller.dto.agendamento;

import com.hav.imobiliaria.controller.dto.endereco.EnderecoListagemImovelDTO;
import com.hav.imobiliaria.controller.dto.endereco.EnderecoVisitaDTO;

import java.time.LocalDateTime;

public record AgendamentoListagemDTO(
        LocalDateTime horario,
        EnderecoVisitaDTO endereco,
        String nomeUsuario,
        Long idImovel,
        String referenciaImagemPrincipal
) {
}
