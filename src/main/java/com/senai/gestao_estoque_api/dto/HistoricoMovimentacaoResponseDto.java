package com.senai.gestao_estoque_api.dto;

import com.senai.gestao_estoque_api.model.MovimentacaoEstoque;
import com.senai.gestao_estoque_api.model.TipoMovimentacao;
import java.time.LocalDateTime;

public class HistoricoMovimentacaoResponseDto {

    private Long id;
    private Long produtoId;
    private String produtoNome;
    private LocalDateTime dataHora;
    private TipoMovimentacao tipo;
    private Integer quantidade;
    private String motivo;

    public HistoricoMovimentacaoResponseDto() {
    }

    public HistoricoMovimentacaoResponseDto(MovimentacaoEstoque movimentacao) {
        this.id = movimentacao.getId();
        this.produtoId = movimentacao.getProduto().getId();
        this.produtoNome = movimentacao.getProduto().getNome();
        this.dataHora = movimentacao.getDataHora();
        this.tipo = movimentacao.getTipo();
        this.quantidade = movimentacao.getQuantidade();
        this.motivo = movimentacao.getMotivo();
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

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
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