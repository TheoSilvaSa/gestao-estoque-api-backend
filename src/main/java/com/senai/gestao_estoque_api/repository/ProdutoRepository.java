package com.senai.gestao_estoque_api.repository;

import com.senai.gestao_estoque_api.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}