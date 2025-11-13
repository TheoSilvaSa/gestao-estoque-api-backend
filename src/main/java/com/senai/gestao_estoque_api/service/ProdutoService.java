package com.senai.gestao_estoque_api.service;

import com.senai.gestao_estoque_api.dto.ProdutoRequestDto;
import com.senai.gestao_estoque_api.dto.ProdutoResponseDto;
import com.senai.gestao_estoque_api.model.Produto;
import com.senai.gestao_estoque_api.repository.ProdutoRepository;
import com.senai.gestao_estoque_api.service.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    @Transactional(readOnly = true)
    public List<ProdutoResponseDto> listarTodos() {
        List<Produto> produtos = produtoRepository.findAll();
        return produtos.stream()
                .map(produto -> new ProdutoResponseDto(produto))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ProdutoResponseDto buscarPorId(Long id) {
        Produto produto = produtoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com ID: " + id));
        return new ProdutoResponseDto(produto);
    }

    @Transactional
    public ProdutoResponseDto criarProduto(ProdutoRequestDto dto) {
        Produto novoProduto = new Produto();
        novoProduto.setNome(dto.getNome());
        novoProduto.setCategoria(dto.getCategoria());
        novoProduto.setPrecoUnitario(dto.getPrecoUnitario());
        novoProduto.setQuantidadeEmEstoque(dto.getQuantidadeEmEstoque());

        Produto produtoSalvo = produtoRepository.save(novoProduto);

        return new ProdutoResponseDto(produtoSalvo);
    }

    @Transactional
    public ProdutoResponseDto atualizarProduto(Long id, ProdutoRequestDto dto) {
        Produto produto = produtoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com ID: " + id));

        produto.setNome(dto.getNome());
        produto.setCategoria(dto.getCategoria());
        produto.setPrecoUnitario(dto.getPrecoUnitario());
        produto.setQuantidadeEmEstoque(dto.getQuantidadeEmEstoque());

        Produto produtoAtualizado = produtoRepository.save(produto);
        return new ProdutoResponseDto(produtoAtualizado);
    }

    @Transactional
    public void deletarProduto(Long id) {
        Produto produto = produtoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com ID: " + id));

        produtoRepository.delete(produto);
    }
}