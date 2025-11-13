package com.senai.gestao_estoque_api.service;

import com.senai.gestao_estoque_api.dto.EstoqueRequestDto;
import com.senai.gestao_estoque_api.dto.HistoricoMovimentacaoResponseDto;
import com.senai.gestao_estoque_api.model.MovimentacaoEstoque;
import com.senai.gestao_estoque_api.model.Produto;
import com.senai.gestao_estoque_api.model.TipoMovimentacao;
import com.senai.gestao_estoque_api.repository.MovimentacaoEstoqueRepository;
import com.senai.gestao_estoque_api.repository.ProdutoRepository;
import com.senai.gestao_estoque_api.service.exception.BusinessException;
import com.senai.gestao_estoque_api.service.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EstoqueService {

    private final ProdutoRepository produtoRepository;
    private final MovimentacaoEstoqueRepository movimentacaoEstoqueRepository;

    public EstoqueService(ProdutoRepository produtoRepository, MovimentacaoEstoqueRepository movimentacaoEstoqueRepository) {
        this.produtoRepository = produtoRepository;
        this.movimentacaoEstoqueRepository = movimentacaoEstoqueRepository;
    }

    @Transactional
    public HistoricoMovimentacaoResponseDto registrarMovimentacao(EstoqueRequestDto dto) {

        if (dto.getTipo() == TipoMovimentacao.SAIDA_VENDA) {
            throw new BusinessException("Não é possível registrar SAÍDA POR VENDA manualmente.");
        }

        if (dto.getTipo() == TipoMovimentacao.AJUSTE && (dto.getMotivo() == null || dto.getMotivo().isBlank())) {
            throw new BusinessException("O motivo é obrigatório para movimentações do tipo AJUSTE.");
        }

        Produto produto = produtoRepository.findById(dto.getProdutoId())
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com ID: " + dto.getProdutoId()));

        int quantidadeAtual = produto.getQuantidadeEmEstoque();
        int quantidadeMovimentada = dto.getQuantidade();
        int novoEstoque = quantidadeAtual + quantidadeMovimentada;

        if (novoEstoque < 0) {
            throw new BusinessException("Operação inválida. O estoque do produto não pode ficar negativo.");
        }

        produto.setQuantidadeEmEstoque(novoEstoque);
        produtoRepository.save(produto);

        MovimentacaoEstoque movimentacao = new MovimentacaoEstoque();
        movimentacao.setProduto(produto);
        movimentacao.setDataHora(LocalDateTime.now());
        movimentacao.setTipo(dto.getTipo());
        movimentacao.setQuantidade(quantidadeMovimentada);
        movimentacao.setMotivo(dto.getMotivo());

        MovimentacaoEstoque movimentacaoSalva = movimentacaoEstoqueRepository.save(movimentacao);

        return new HistoricoMovimentacaoResponseDto(movimentacaoSalva);
    }

    @Transactional(readOnly = true)
    public List<HistoricoMovimentacaoResponseDto> listarHistorico() {
        List<MovimentacaoEstoque> movimentacoes = movimentacaoEstoqueRepository.findAll();

        return movimentacoes.stream()
                .map(mov -> new HistoricoMovimentacaoResponseDto(mov))
                .collect(Collectors.toList());
    }
}