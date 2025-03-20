package com.hav.imobiliaria.controller.dto.agendamento;

import com.hav.imobiliaria.controller.dto.endereco.EnderecoListagemImovelDTO;
import com.hav.imobiliaria.controller.dto.endereco.EnderecoVisitaDTO;
import com.hav.imobiliaria.model.enums.StatusAgendamentoEnum;

import java.time.LocalDateTime;

public record AgendamentoListagemDTO(
        Long id,
        LocalDateTime horario,
        EnderecoVisitaDTO endereco,
        String nomeUsuario,
        Long idImovel,
        String referenciaImagemPrincipal,
        StatusAgendamentoEnum status
) {
}
