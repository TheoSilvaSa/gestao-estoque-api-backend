package com.senai.gestao_estoque_api.service;

import com.senai.gestao_estoque_api.dto.RelatorioFiltroDto;
import com.senai.gestao_estoque_api.dto.RelatorioResponseDto;
import com.senai.gestao_estoque_api.dto.VendaResponseDto;
import com.senai.gestao_estoque_api.model.ItemVenda;
import com.senai.gestao_estoque_api.model.Venda;
import com.senai.gestao_estoque_api.repository.VendaRepository;
import com.senai.gestao_estoque_api.repository.specification.VendaSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RelatorioService {

    private final VendaRepository vendaRepository;

    public RelatorioService(VendaRepository vendaRepository) {
        this.vendaRepository = vendaRepository;
    }

    @Transactional(readOnly = true)
    public RelatorioResponseDto gerarRelatorio(RelatorioFiltroDto filtros) {

        Specification<Venda> spec = Specification.where(
                VendaSpecification.porData(filtros.getDataInicial(), filtros.getDataFinal())
        ).and(
                VendaSpecification.porValor(filtros.getValorMinimo(), filtros.getValorMaximo())
        ).and(
                VendaSpecification.porUsuario(filtros.getUsuarioId())
        );

        List<Venda> vendasFiltradas = vendaRepository.findAll(spec);

        BigDecimal totalVendas = BigDecimal.ZERO;
        long totalItensVendidos = 0;

        for (Venda venda : vendasFiltradas) {
            totalVendas = totalVendas.add(venda.getValorTotal());

            for (ItemVenda item : venda.getItens()) {
                totalItensVendidos += item.getQuantidade();
            }
        }

        List<VendaResponseDto> vendasDto = vendasFiltradas.stream()
                .map(venda -> new VendaResponseDto(venda))
                .collect(Collectors.toList());

        return new RelatorioResponseDto(totalVendas, totalItensVendidos, vendasDto);
    }
}