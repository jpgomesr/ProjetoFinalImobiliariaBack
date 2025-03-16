package com.hav.imobiliaria.repository.specs;

import com.hav.imobiliaria.model.entity.Imovel;
import com.hav.imobiliaria.model.enums.TipoFinalidadeEnum;
import com.hav.imobiliaria.model.enums.TipoImovelEnum;
import org.springframework.data.jpa.domain.Specification;

public class ImovelSpecs {


    public static Specification<Imovel> tituloLike(String titulo) {
        return (root, query, cb) -> cb.like(cb.upper(root.get("titulo")), "%" + titulo.toUpperCase() + "%");
    }

    public static Specification<Imovel> descricaoLike(String descricao) {
        return (root, query, cb) -> cb.like(cb.upper(root.get("descricao")), "%" + descricao.toUpperCase() + "%");
    }

    public static Specification<Imovel> tipoResidenciaEqual(TipoImovelEnum tipoResidencia) {
        return (root, query, cb) -> cb.equal(root.join("endereco").get("tipoResidencia"), tipoResidencia);
    }

    public static Specification<Imovel> tamanhoBeetween(Integer tamanhoMinimo, Integer tamanhoMaximo) {
        return (root, query, cb) -> cb.between(root.get("tamanho"), tamanhoMinimo, tamanhoMaximo);
    }
    public static Specification<Imovel> tamanhoMin(Integer tamanhoMinimo) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get("tamanho"), tamanhoMinimo);
    }
    public static Specification<Imovel> tamanhoMax(Integer tamanhoMaximo) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.get("tamanho"), tamanhoMaximo);
    }

    public static Specification<Imovel> qtdQuartosEqual(Integer qtdQuartos) {
        return (root, query, cb) -> {
            return cb.equal(root.get("qtdQuartos"), qtdQuartos);
        };
    }
    public static Specification<Imovel> qtdQuartosEqualOrGratherThan(Integer qtdQuartos) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("qtdQuartos"), qtdQuartos);
    }
    public static Specification<Imovel> qtdGaragemEqualOrGratherThan(Integer qtdGaragem) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("qtdGaragens"), qtdGaragem);
    }
    public static Specification<Imovel> precoBetween(Double precoMin, Double precoMax) {
        return (root, query, cb) -> cb.between(root.get("preco"), precoMin, precoMax);
    }
    public static Specification<Imovel> precoMin(Double precoMin) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get("preco"), precoMin);
    }
    public static Specification<Imovel> precoMax(Double precoMin) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.get("preco"), precoMin);
    }

    public static Specification<Imovel> finalidadeEqual(TipoFinalidadeEnum finalidade) {
        return (root, query, cb) -> cb.equal(root.get("finalidade"), finalidade);
    }

    public static Specification<Imovel> permitirDestaqueEqual(Boolean permitirDestaque) {
        return (root, query, cb) -> cb.equal(root.get("permitirDestaque"), permitirDestaque);
    }


    public static Specification<Imovel> enderecoBairroEqual(String bairro) {
        return (root, query, cb) -> cb.equal(cb.upper(root.get("endereco").get("bairro")), bairro.toUpperCase());
    }

    public static Specification<Imovel> enderecoCidadeEqual(String cidade) {
        System.out.println(cidade.toUpperCase());
        return (root, query, cb) -> cb.equal(cb.upper(root.get("endereco").get("cidade")), cidade.toUpperCase());
    }


    public static Specification<Imovel> qtdGaragensEqual(Integer qtdGaragens) {
        return (root, query, cb) -> cb.equal(root.get("qtdGaragens"), qtdGaragens);
    }

    public static Specification<Imovel> qtdBanheirosEqual(Integer qtdBanheiros) {
        return (root, query, cb) -> cb.equal(root.get("qtdBanheiros"), qtdBanheiros);
    }

    public static Specification<Imovel> ativo(Boolean ativo) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("ativo"), ativo);
    }


}
