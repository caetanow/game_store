package com.generation.loja_game.repository;

import com.generation.loja_game.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    public List<Categoria> findAllByTipoContainingIgnoreCase(@Param("tipo") String tipo);
}
