package com.generation.loja_game.controller;

import com.generation.loja_game.model.Categoria;
import com.generation.loja_game.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/categorias")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CategoriaController {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @GetMapping
    private ResponseEntity<List<Categoria>> getAll() {
        return ResponseEntity.ok(categoriaRepository.findAll());
    }

    @GetMapping("/{id}")
    private ResponseEntity<Categoria> getById(@PathVariable Long id){
        return categoriaRepository.findById(id)
                .map(resposta -> ResponseEntity.ok(resposta))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/tipo/{tipo}")
    private ResponseEntity<List<Categoria>> getByTipo(@PathVariable String tipo){
        return ResponseEntity.ok(categoriaRepository.findAllByTipoContainingIgnoreCase(tipo));
    }

    @PostMapping
    public ResponseEntity<Categoria> postCategoria(@Valid @RequestBody Categoria categoria){
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaRepository.save(categoria));
    }

    @PutMapping
    public ResponseEntity<Categoria> putCategoria(@Valid @RequestBody Categoria categoria){
        return categoriaRepository.findById(categoria.getId())
                .map(resposta -> ResponseEntity.ok().body(categoriaRepository.save(categoria)))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?>deleteCategoria(@PathVariable("id") Long id) {
        return categoriaRepository.findById(id)
                .map(resposta -> {
                    categoriaRepository.deleteById(id);
                    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
                })
                .orElse(ResponseEntity.notFound().build());
    }


}
