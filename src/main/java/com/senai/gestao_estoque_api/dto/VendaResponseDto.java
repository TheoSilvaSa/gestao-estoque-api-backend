package com.senai.gestao_estoque_api.dto;

import com.senai.gestao_estoque_api.model.Venda;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class VendaResponseDto {

    private Long id;
    private LocalDateTime dataHora;
    private BigDecimal valorTotal;
    private BigDecimal valorRecebido;
    private BigDecimal troco;
    private String usuarioResponsavel;
    private Long quantidadeTotalItens; // O novo campo
    private List<ItemVendaResponseDto> itens;

    public VendaResponseDto() {
    }

    public VendaResponseDto(Venda venda) {
        this.id = venda.getId();
        this.dataHora = venda.getDataHora();
        this.valorTotal = venda.getValorTotal();
        this.valorRecebido = venda.getValorRecebido();
        this.troco = venda.getTroco();
        this.usuarioResponsavel = venda.getUsuarioResponsavel().getNomeCompleto();

        this.itens = venda.getItens().stream()
                .map(item -> new ItemVendaResponseDto(item))
                .collect(Collectors.toList());
        this.quantidadeTotalItens = venda.getItens().stream()
                .mapToLong(item -> item.getQuantidade())
                .sum();
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public BigDecimal getValorRecebido() {
        return valorRecebido;
    }

    public void setValorRecebido(BigDecimal valorRecebido) {
        this.valorRecebido = valorRecebido;
    }

    public BigDecimal getTroco() {
        return troco;
    }

    public void setTroco(BigDecimal troco) {
        this.troco = troco;
    }

    public String getUsuarioResponsavel() {
        return usuarioResponsavel;
    }

    public void setUsuarioResponsavel(String usuarioResponsavel) {
        this.usuarioResponsavel = usuarioResponsavel;
    }

    public Long getQuantidadeTotalItens() {
        return quantidadeTotalItens;
    }

    public void setQuantidadeTotalItens(Long quantidadeTotalItens) {
        this.quantidadeTotalItens = quantidadeTotalItens;
    }

    public List<ItemVendaResponseDto> getItens() {
        return itens;
    }

    public void setItens(List<ItemVendaResponseDto> itens) {
        this.itens = itens;
    }
}