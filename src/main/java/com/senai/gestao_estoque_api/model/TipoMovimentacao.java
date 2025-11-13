package com.senai.gestao_estoque_api.model;

public enum TipoMovimentacao {
    ENTRADA,     // (Reposição)
    AJUSTE,      // (Correção)
    SAIDA_VENDA  // (Baixa automática por venda)
}