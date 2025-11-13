package com.senai.gestao_estoque_api.controller;

import com.senai.gestao_estoque_api.dto.VendaRequestDto;
import com.senai.gestao_estoque_api.dto.VendaResponseDto;
import com.senai.gestao_estoque_api.service.VendaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/vendas")
public class VendaController {

    private final VendaService vendaService;

    public VendaController(VendaService vendaService) {
        this.vendaService = vendaService;
    }

    @PostMapping
    public ResponseEntity<VendaResponseDto> registrarVenda(@Valid @RequestBody VendaRequestDto dto) {
        VendaResponseDto vendaRegistrada = vendaService.registrarVenda(dto);

        return ResponseEntity.ok(vendaRegistrada);
    }
}