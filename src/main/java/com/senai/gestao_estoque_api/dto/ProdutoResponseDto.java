package com.senai.gestao_estoque_api.dto;

import com.senai.gestao_estoque_api.model.Produto;
import java.math.BigDecimal;

public class ProdutoResponseDto {

    private Long id; //
    private String nome;
    private String categoria;
    private Integer quantidadeEmEstoque;
    private BigDecimal precoUnitario;

    public ProdutoResponseDto() {
    }

    public ProdutoResponseDto(Produto produto) {
        this.id = produto.getId();
        this.nome = produto.getNome();
        this.categoria = produto.getCategoria();
        this.quantidadeEmEstoque = produto.getQuantidadeEmEstoque();
        this.precoUnitario = produto.getPrecoUnitario();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Integer getQuantidadeEmEstoque() {
        return quantidadeEmEstoque;
    }

    public void setQuantidadeEmEstoque(Integer quantidadeEmEstoque) {
        this.quantidadeEmEstoque = quantidadeEmEstoque;
    }

    public BigDecimal getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(BigDecimal precoUnitario) {
        this.precoUnitario = precoUnitario;
    }
}