package com.tecnoshop.api.controller;

import com.tecnoshop.api.dto.ProductoDTO;
import com.tecnoshop.api.entity.Producto;
import com.tecnoshop.api.exception.ProductoNoEncontradoException;
import com.tecnoshop.api.service.ProductoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping
    public ResponseEntity<Page<Producto>> getAllProductos(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) Long categoriaId,
            Pageable pageable) {
        Page<Producto> productos = productoService.obtenerTodosLosProductos(nombre, categoriaId, pageable);
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> getProductoById(@PathVariable Long id) {
        return productoService.obtenerProductoPorId(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ProductoNoEncontradoException("Producto no encontrado con ID: " + id));
    }

    @PostMapping
    public ResponseEntity<Producto> createProducto(@Valid @RequestBody ProductoDTO productoDTO) {
        Producto nuevoProducto = productoService.crearProducto(productoDTO);
        return new ResponseEntity<>(nuevoProducto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Producto> updateProducto(@PathVariable Long id, @Valid @RequestBody ProductoDTO productoDTO) {
        Producto productoActualizado = productoService.actualizarProducto(id, productoDTO);
        return ResponseEntity.ok(productoActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProducto(@PathVariable Long id) {
        productoService.eliminarProducto(id);
        return ResponseEntity.noContent().build();
    }
}
