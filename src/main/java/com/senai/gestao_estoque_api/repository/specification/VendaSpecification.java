package com.senai.gestao_estoque_api.repository.specification;

import com.senai.gestao_estoque_api.model.Usuario;
import com.senai.gestao_estoque_api.model.Venda;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class VendaSpecification {

    public static Specification<Venda> porData(LocalDateTime dataInicial, LocalDateTime dataFinal) {
        return (root, query, cb) -> {
            if (dataInicial != null && dataFinal != null) {
                return cb.between(root.get("dataHora"), dataInicial, dataFinal);
            }
            if (dataInicial != null) {
                return cb.greaterThanOrEqualTo(root.get("dataHora"), dataInicial);
            }
            if (dataFinal != null) {
                return cb.lessThanOrEqualTo(root.get("dataHora"), dataFinal);
            }
            return cb.conjunction();
        };
    }

    public static Specification<Venda> porValor(BigDecimal valorMinimo, BigDecimal valorMaximo) {
        return (root, query, cb) -> {
            if (valorMinimo != null && valorMaximo != null) {
                return cb.between(root.get("valorTotal"), valorMinimo, valorMaximo);
            }
            if (valorMinimo != null) {
                return cb.greaterThanOrEqualTo(root.get("valorTotal"), valorMinimo);
            }
            if (valorMaximo != null) {
                return cb.lessThanOrEqualTo(root.get("valorTotal"), valorMaximo);
            }
            return cb.conjunction();
        };
    }

    public static Specification<Venda> porUsuario(Long usuarioId) {
        return (root, query, cb) -> {
            if (usuarioId == null) {
                return cb.conjunction();
            }
            Join<Venda, Usuario> usuarioJoin = root.join("usuarioResponsavel");
            return cb.equal(usuarioJoin.get("id"), usuarioId);
        };
    }
}