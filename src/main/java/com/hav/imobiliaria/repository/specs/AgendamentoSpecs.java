package com.hav.imobiliaria.repository.specs;

import com.hav.imobiliaria.model.entity.Agendamento;
import com.hav.imobiliaria.model.entity.Corretor;
import com.hav.imobiliaria.model.entity.Imovel;
import com.hav.imobiliaria.model.enums.StatusAgendamentoEnum;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class AgendamentoSpecs {

    public static Specification<Agendamento> statusEquals(StatusAgendamentoEnum status) {
        return (root, query, cb) -> cb.equal(root.get("status"),status);
    }
    public static Specification<Agendamento> dataEquals(LocalDate data) {
        return (Root<Agendamento> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            Predicate anoIgual = cb.equal(cb.function("year", Integer.class, root.get("dataHora")), data.getYear());
            Predicate mesIgual = cb.equal(cb.function("month", Integer.class, root.get("dataHora")), data.getMonthValue());
            Predicate diaIgual = cb.equal(cb.function("day", Integer.class, root.get("dataHora")), data.getDayOfMonth());

            return cb.and(anoIgual, mesIgual, diaIgual);
        };
    }
    public static Specification<Agendamento> idCorretorEquals(Long id) {
        return (root, query, cb) ->
                cb.equal(root.join("corretor").get("id"), id);
    }
    public static Specification<Agendamento> idUsuarioEquals(Long id) {
        return (root, query, cb) ->
                cb.equal(root.join("usuarioComum").get("id"), id);
    }
}
