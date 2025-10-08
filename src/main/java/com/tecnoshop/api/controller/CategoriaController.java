package com.tecnoshop.api.controller;

import com.tecnoshop.api.dto.CategoriaDTO;
import com.tecnoshop.api.entity.Categoria;
import com.tecnoshop.api.exception.CategoriaNoEncontradaException;
import com.tecnoshop.api.service.CategoriaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping
    public ResponseEntity<List<Categoria>> getAllCategorias() {
        List<Categoria> categorias = categoriaService.obtenerTodasLasCategorias();
        return ResponseEntity.ok(categorias);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Categoria> getCategoriaById(@PathVariable Long id) {
        return categoriaService.obtenerCategoriaPorId(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new CategoriaNoEncontradaException("Categoría no encontrada con ID: " + id));
    }

    @PostMapping
    public ResponseEntity<Categoria> createCategoria(@Valid @RequestBody CategoriaDTO categoriaDTO) {
        Categoria nuevaCategoria = categoriaService.crearCategoria(categoriaDTO);
        return new ResponseEntity<>(nuevaCategoria, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Categoria> updateCategoria(@PathVariable Long id, @Valid @RequestBody CategoriaDTO categoriaDTO) {
        Categoria categoriaActualizada = categoriaService.actualizarCategoria(id, categoriaDTO);
        return ResponseEntity.ok(categoriaActualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategoria(@PathVariable Long id) {
        categoriaService.eliminarCategoria(id);
        return ResponseEntity.noContent().build();
    }
}
