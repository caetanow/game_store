package com.generation.loja_game.repository;

import com.generation.loja_game.model.Produtos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface ProdutosRepository extends JpaRepository<Produtos, Long> {

    public List<Produtos> findAllByNomeContainingIgnoreCase(@Param("nome") String nome);

    public List<Produtos> findByPrecoGreaterThanOrderByPreco(BigDecimal preco);

    public List<Produtos> findByPrecoLessThanOrderByPrecoDesc(BigDecimal preco);
}
