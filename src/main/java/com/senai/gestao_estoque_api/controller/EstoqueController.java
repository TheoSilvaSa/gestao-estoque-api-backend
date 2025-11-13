package com.senai.gestao_estoque_api.controller;

import com.senai.gestao_estoque_api.dto.EstoqueRequestDto;
import com.senai.gestao_estoque_api.dto.HistoricoMovimentacaoResponseDto;
import com.senai.gestao_estoque_api.service.EstoqueService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/estoque")
public class EstoqueController {

    private final EstoqueService estoqueService;

    public EstoqueController(EstoqueService estoqueService) {
        this.estoqueService = estoqueService;
    }

    @PostMapping("/movimentar")
    public ResponseEntity<HistoricoMovimentacaoResponseDto> registrarMovimentacao(@Valid @RequestBody EstoqueRequestDto dto) {
        HistoricoMovimentacaoResponseDto historico = estoqueService.registrarMovimentacao(dto);
        return ResponseEntity.ok(historico);
    }

    @GetMapping("/historico")
    public ResponseEntity<List<HistoricoMovimentacaoResponseDto>> listarHistorico() {
        List<HistoricoMovimentacaoResponseDto> historico = estoqueService.listarHistorico();
        return ResponseEntity.ok(historico);
    }
}