package com.senai.gestao_estoque_api.dto;

import com.senai.gestao_estoque_api.model.ItemVenda;
import java.math.BigDecimal;

public class ItemVendaResponseDto {

    private Long id;
    private Long produtoId;
    private String produtoNome;
    private Integer quantidade;
    private BigDecimal precoUnitario;
    private BigDecimal subtotal;

    public ItemVendaResponseDto() {
    }

    public ItemVendaResponseDto(ItemVenda itemVenda) {
        this.id = itemVenda.getId();
        this.produtoId = itemVenda.getProduto().getId();
        this.produtoNome = itemVenda.getProduto().getNome();
        this.quantidade = itemVenda.getQuantidade();
        this.precoUnitario = itemVenda.getPrecoUnitarioMomento();
        this.subtotal = itemVenda.getSubtotal();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(Long produtoId) {
        this.produtoId = produtoId;
    }

    public String getProdutoNome() {
        return produtoNome;
    }

    public void setProdutoNome(String produtoNome) {
        this.produtoNome = produtoNome;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(BigDecimal precoUnitario) {
        this.precoUnitario = precoUnitario;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }
}