package com.senai.gestao_estoque_api.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

public class VendaRequestDto {

    @NotNull(message = "Valor recebido é obrigatório")
    @DecimalMin(value = "0.01", message = "Valor recebido deve ser positivo")
    private BigDecimal valorRecebido; //

    @NotNull
    @NotEmpty(message = "A venda deve conter pelo menos um item")
    @Valid
    private List<ItemVendaRequestDto> itens;

    public VendaRequestDto() {
    }

    public BigDecimal getValorRecebido() {
        return valorRecebido;
    }

    public void setValorRecebido(BigDecimal valorRecebido) {
        this.valorRecebido = valorRecebido;
    }

    public List<ItemVendaRequestDto> getItens() {
        return itens;
    }

    public void setItens(List<ItemVendaRequestDto> itens) {
        this.itens = itens;
    }
}