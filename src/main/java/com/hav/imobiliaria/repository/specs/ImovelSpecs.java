package com.hav.imobiliaria.repository.specs;

import com.hav.imobiliaria.model.Imovel;
import com.hav.imobiliaria.model.TipoFinalidadeEnum;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

public class ImovelSpecs {

    public static Specification<Imovel> tituloLike(String titulo) {
        return (root, query, cb) -> cb.like(cb.upper(root.get("titulo")), "%" + titulo.toUpperCase() + "%");
    }

    public static Specification<Imovel> tipoResidenciaEqual(String tipoResidencia) {
        return (root, query, cb) -> cb.equal(root.get("tipoResidencia"), tipoResidencia);
    }

    public static Specification<Imovel> tamanhoGreaterThanEqual(Integer tamanho) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get("tamanho"), tamanho);
    }

    public static Specification<Imovel> qtdQuartosEqual(Integer qtdQuartos) {
        return (root, query, cb) -> {
            System.out.println("Filtrando por qtdQuartos: " + qtdQuartos);
            return cb.equal(root.get("qtdQuartos"), qtdQuartos);
        };
    }
    public static Specification<Imovel> precoBetween(Double precoMin, Double precoMax) {
        return (root, query, cb) -> cb.between(root.get("preco"), precoMin, precoMax);
    }

    public static Specification<Imovel> finalidadeEqual(TipoFinalidadeEnum finalidade) {
        return (root, query, cb) -> cb.equal(root.get("finalidade"), finalidade);
    }

    public static Specification<Imovel> permitirDestaqueEqual(Boolean permitirDestaque) {
        return (root, query, cb) -> cb.equal(root.get("permitirDestaque"), permitirDestaque);
    }

    public static Specification<Imovel> enderecoCidadeLike(String cidade) {
        return (root, query, cb) -> {
            Join<Object, Object> joinEndereco = root.join("endereco", JoinType.LEFT);
            return cb.like(cb.upper(joinEndereco.get("cidade")), "%" + cidade.toUpperCase() + "%");
        };
    }

    public static Specification<Imovel> qtdGaragensGreaterThanEqual(Integer qtdGaragens) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get("qtdGaragens"), qtdGaragens);
    }

    public static Specification<Imovel> qtdBanheirosGreaterThanEqual(Integer qtdBanheiros) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get("qtdBanheiros"), qtdBanheiros);
    }
}
