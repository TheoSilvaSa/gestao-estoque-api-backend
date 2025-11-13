package com.senai.gestao_estoque_api.service;

import com.senai.gestao_estoque_api.dto.ItemVendaRequestDto;
import com.senai.gestao_estoque_api.dto.VendaRequestDto;
import com.senai.gestao_estoque_api.dto.VendaResponseDto;
import com.senai.gestao_estoque_api.model.*;
import com.senai.gestao_estoque_api.repository.MovimentacaoEstoqueRepository;
import com.senai.gestao_estoque_api.repository.ProdutoRepository;
import com.senai.gestao_estoque_api.repository.UsuarioRepository;
import com.senai.gestao_estoque_api.repository.VendaRepository;
import com.senai.gestao_estoque_api.service.exception.BusinessException;
import com.senai.gestao_estoque_api.service.exception.ResourceNotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class VendaService {

    private final VendaRepository vendaRepository;
    private final ProdutoRepository produtoRepository;
    private final UsuarioRepository usuarioRepository;
    private final MovimentacaoEstoqueRepository movimentacaoEstoqueRepository;

    public VendaService(VendaRepository vendaRepository,
                        ProdutoRepository produtoRepository,
                        UsuarioRepository usuarioRepository,
                        MovimentacaoEstoqueRepository movimentacaoEstoqueRepository) {
        this.vendaRepository = vendaRepository;
        this.produtoRepository = produtoRepository;
        this.usuarioRepository = usuarioRepository;
        this.movimentacaoEstoqueRepository = movimentacaoEstoqueRepository;
    }

    @Transactional
    public VendaResponseDto registrarVenda(VendaRequestDto dto) {

        Usuario usuarioLogado = getUsuarioLogado();

        Venda novaVenda = new Venda();
        novaVenda.setDataHora(LocalDateTime.now());
        novaVenda.setUsuarioResponsavel(usuarioLogado);
        novaVenda.setValorRecebido(dto.getValorRecebido());

        BigDecimal valorTotalCalculado = BigDecimal.ZERO;
        List<ItemVenda> itensParaSalvar = new ArrayList<>();

        for (ItemVendaRequestDto itemDto : dto.getItens()) {

            Produto produto = produtoRepository.findById(itemDto.getProdutoId()).orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com ID: " + itemDto.getProdutoId()));

            int qtdDesejada = itemDto.getQuantidade();
            int qtdEmEstoque = produto.getQuantidadeEmEstoque();

            if (qtdDesejada > qtdEmEstoque) {
                throw new BusinessException("Estoque insuficiente para o produto: " + produto.getNome()
                        + ". Desejado: " + qtdDesejada + ", Em estoque: " + qtdEmEstoque);
            }

            ItemVenda itemVenda = new ItemVenda();
            itemVenda.setProduto(produto);
            itemVenda.setQuantidade(qtdDesejada);
            itemVenda.setPrecoUnitarioMomento(produto.getPrecoUnitario());

            BigDecimal subtotal = produto.getPrecoUnitario().multiply(BigDecimal.valueOf(qtdDesejada));
            itemVenda.setSubtotal(subtotal);

            novaVenda.adicionarItem(itemVenda);
            valorTotalCalculado = valorTotalCalculado.add(subtotal);

            produto.setQuantidadeEmEstoque(qtdEmEstoque - qtdDesejada);
            produtoRepository.save(produto);

            registrarMovimentacaoEstoque(produto, -qtdDesejada);
        }

        novaVenda.setValorTotal(valorTotalCalculado);

        if (dto.getValorRecebido().compareTo(valorTotalCalculado) < 0) {
            throw new BusinessException("Valor recebido (R$ " + dto.getValorRecebido()
                    + ") é menor que o valor total (R$ " + valorTotalCalculado + ").");
        }

        BigDecimal troco = dto.getValorRecebido().subtract(valorTotalCalculado);
        novaVenda.setTroco(troco);

        Venda vendaSalva = vendaRepository.save(novaVenda);

        return new VendaResponseDto(vendaSalva);
    }

    private Usuario getUsuarioLogado() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof Usuario) {
            return (Usuario) principal;
        } else {
            String email = ((org.springframework.security.core.userdetails.UserDetails) principal).getUsername();
            return usuarioRepository.findByEmail(email)
                    .orElseThrow(() -> new ResourceNotFoundException("Usuário logado não encontrado no banco de dados."));
        }
    }

    private void registrarMovimentacaoEstoque(Produto produto, Integer quantidade) {
        MovimentacaoEstoque movimentacao = new MovimentacaoEstoque();
        movimentacao.setProduto(produto);
        movimentacao.setDataHora(LocalDateTime.now());
        movimentacao.setTipo(TipoMovimentacao.SAIDA_VENDA);
        movimentacao.setQuantidade(quantidade);
        movimentacao.setMotivo("Venda registrada");
        movimentacaoEstoqueRepository.save(movimentacao);
    }
}