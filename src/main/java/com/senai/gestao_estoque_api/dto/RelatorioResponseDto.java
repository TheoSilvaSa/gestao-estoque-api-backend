package com.senai.gestao_estoque_api.dto;

import java.math.BigDecimal;
import java.util.List;

public class RelatorioResponseDto {

    private BigDecimal totalVendas; //
    private Long totalItensVendidos; //

    private List<VendaResponseDto> vendasFiltradas;

    public RelatorioResponseDto() {
    }

    public RelatorioResponseDto(BigDecimal totalVendas, Long totalItensVendidos, List<VendaResponseDto> vendasFiltradas) {
        this.totalVendas = totalVendas;
        this.totalItensVendidos = totalItensVendidos;
        this.vendasFiltradas = vendasFiltradas;
    }

    public BigDecimal getTotalVendas() {
        return totalVendas;
    }

    public void setTotalVendas(BigDecimal totalVendas) {
        this.totalVendas = totalVendas;
    }

    public Long getTotalItensVendidos() {
        return totalItensVendidos;
    }

    public void setTotalItensVendidos(Long totalItensVendidos) {
        this.totalItensVendidos = totalItensVendidos;
    }

    public List<VendaResponseDto> getVendasFiltradas() {
        return vendasFiltradas;
    }

    public void setVendasFiltradas(List<VendaResponseDto> vendasFiltradas) {
        this.vendasFiltradas = vendasFiltradas;
    }
}