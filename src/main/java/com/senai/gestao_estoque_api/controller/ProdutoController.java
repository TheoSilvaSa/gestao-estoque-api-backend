package com.senai.gestao_estoque_api.controller;

import com.senai.gestao_estoque_api.dto.ProdutoRequestDto;
import com.senai.gestao_estoque_api.dto.ProdutoResponseDto;
import com.senai.gestao_estoque_api.service.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @GetMapping
    public ResponseEntity<List<ProdutoResponseDto>> listarTodos() {
        List<ProdutoResponseDto> produtos = produtoService.listarTodos();
        return ResponseEntity.ok(produtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponseDto> buscarPorId(@PathVariable Long id) {
        ProdutoResponseDto produto = produtoService.buscarPorId(id);
        return ResponseEntity.ok(produto);
    }

    @PostMapping
    public ResponseEntity<ProdutoResponseDto> criarProduto(@Valid @RequestBody ProdutoRequestDto dto) {
        ProdutoResponseDto novoProduto = produtoService.criarProduto(dto);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(novoProduto.getId()).toUri();

        return ResponseEntity.created(uri).body(novoProduto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoResponseDto> atualizarProduto(@PathVariable Long id, @Valid @RequestBody ProdutoRequestDto dto) {
        ProdutoResponseDto produtoAtualizado = produtoService.atualizarProduto(id, dto);
        return ResponseEntity.ok(produtoAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarProduto(@PathVariable Long id) {
        produtoService.deletarProduto(id);
        return ResponseEntity.noContent().build();
    }
}