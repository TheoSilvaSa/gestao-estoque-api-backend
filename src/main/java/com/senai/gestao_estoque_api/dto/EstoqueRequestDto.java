package com.senai.gestao_estoque_api.dto;

import com.senai.gestao_estoque_api.model.TipoMovimentacao;
import jakarta.validation.constraints.NotNull;

public class EstoqueRequestDto {

    @NotNull(message = "ID do Produto é obrigatório")
    private Long produtoId;

    @NotNull(message = "Tipo de movimentação é obrigatório")
    private TipoMovimentacao tipo;

    @NotNull(message = "Quantidade é obrigatória")
    private Integer quantidade;

    private String motivo;

    public EstoqueRequestDto() {
    }

    public Long getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(Long produtoId) {
        this.produtoId = produtoId;
    }

    public TipoMovimentacao getTipo() {
        return tipo;
    }

    public void setTipo(TipoMovimentacao tipo) {
        this.tipo = tipo;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }
}