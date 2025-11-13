package com.senai.gestao_estoque_api.controller;

import com.senai.gestao_estoque_api.dto.RelatorioFiltroDto;
import com.senai.gestao_estoque_api.dto.RelatorioResponseDto;
import com.senai.gestao_estoque_api.service.RelatorioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/relatorios")
public class RelatorioController {

    private final RelatorioService relatorioService;

    public RelatorioController(RelatorioService relatorioService) {
        this.relatorioService = relatorioService;
    }

    @GetMapping
    public ResponseEntity<RelatorioResponseDto> gerarRelatorio(RelatorioFiltroDto filtros) {
        RelatorioResponseDto relatorio = relatorioService.gerarRelatorio(filtros);
        return ResponseEntity.ok(relatorio);
    }
}