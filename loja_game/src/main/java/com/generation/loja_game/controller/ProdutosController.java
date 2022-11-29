package com.generation.loja_game.controller;

import com.generation.loja_game.model.Produtos;
import com.generation.loja_game.repository.CategoriaRepository;
import com.generation.loja_game.repository.ProdutosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/produtos")
@CrossOrigin(origins = "*",allowedHeaders = "*")
public class ProdutosController {

    @Autowired
    private ProdutosRepository produtosRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @GetMapping
    public ResponseEntity<List<Produtos>> getAll() {
        return ResponseEntity.ok(produtosRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produtos> getById(@PathVariable Long id){
        return produtosRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/titulo/{nome}")
    public ResponseEntity<List<Produtos>> getByNome(@PathVariable String nome){
        return ResponseEntity.ok(produtosRepository.findAllByNomeContainingIgnoreCase(nome));
    }

    @PostMapping
    public ResponseEntity<Produtos> postProdutos(@Valid @RequestBody Produtos produtos) {
        return categoriaRepository.findById(produtos.getCategoria().getId())
                .map(resposta -> ResponseEntity.status(HttpStatus.CREATED).body(produtosRepository.save(produtos)))
                .orElse(ResponseEntity.badRequest().build());
    }

    @PutMapping()
    public ResponseEntity<Produtos> putProdutos(@Valid @RequestBody Produtos produtos){
        if(produtosRepository.existsById(produtos.getId())){
            return categoriaRepository.findById(produtos.getCategoria().getId())
                    .map(resposta -> ResponseEntity.status(HttpStatus.OK).body(produtosRepository.save(produtos)))
                    .orElse(ResponseEntity.badRequest().build());
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProdutos(@PathVariable Long id){
        return produtosRepository.findById(id)
                .map(resposta -> {
                    produtosRepository.deleteById(id);
                    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/preco_maior/{preco}")
    public ResponseEntity<List<Produtos>> getPrecoMaior(@PathVariable BigDecimal preco){
        return ResponseEntity.ok(produtosRepository.findByPrecoGreaterThanOrderByPreco(preco));
    }

    @GetMapping("/preco_menor/{preco}")
    public ResponseEntity<List<Produtos>> getPrecoMenor(@PathVariable BigDecimal preco){
        return ResponseEntity.ok(produtosRepository.findByPrecoLessThanOrderByPrecoDesc(preco));
    }
}
