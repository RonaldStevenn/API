package com.tecnoshop.api.service;

import com.tecnoshop.api.dto.ProductoDTO;
import com.tecnoshop.api.entity.Categoria;
import com.tecnoshop.api.entity.Producto;
import com.tecnoshop.api.exception.CategoriaNoEncontradaException;
import com.tecnoshop.api.exception.ProductoNoEncontradoException;
import com.tecnoshop.api.repository.CategoriaRepository;
import com.tecnoshop.api.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.criteria.Predicate;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Transactional(readOnly = true)
    public Page<Producto> obtenerTodosLosProductos(String nombre, Long categoriaId, Pageable pageable) {
        Specification<Producto> spec = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (nombre != null && !nombre.isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("nombre")), "%" + nombre.toLowerCase() + "%"));
            }

            if (categoriaId != null) {
                predicates.add(criteriaBuilder.equal(root.get("categoria").get("id"), categoriaId));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
        return productoRepository.findAll(spec, pageable);
    }

    @Transactional(readOnly = true)
    public Optional<Producto> obtenerProductoPorId(Long id) {
        return productoRepository.findById(id);
    }

    @Transactional
    public Producto crearProducto(ProductoDTO productoDTO) {
        Producto producto = new Producto();
        producto.setNombre(productoDTO.getNombre());
        producto.setDescripcion(productoDTO.getDescripcion());
        producto.setPrecio(productoDTO.getPrecio());
        producto.setStock(productoDTO.getStock());

        if (productoDTO.getCategoriaId() != null) {
            Categoria categoria = categoriaRepository.findById(productoDTO.getCategoriaId())
                    .orElseThrow(() -> new CategoriaNoEncontradaException("Categoría no encontrada con ID: " + productoDTO.getCategoriaId()));
            producto.setCategoria(categoria);
        }

        return productoRepository.save(producto);
    }

    @Transactional
    public Producto actualizarProducto(Long id, ProductoDTO productoDTO) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ProductoNoEncontradoException("Producto no encontrado con ID: " + id));

        producto.setNombre(productoDTO.getNombre());
        producto.setDescripcion(productoDTO.getDescripcion());
        producto.setPrecio(productoDTO.getPrecio());
        producto.setStock(productoDTO.getStock());

        if (productoDTO.getCategoriaId() != null) {
            if (producto.getCategoria() == null || !producto.getCategoria().getId().equals(productoDTO.getCategoriaId())) {
                Categoria categoria = categoriaRepository.findById(productoDTO.getCategoriaId())
                        .orElseThrow(() -> new CategoriaNoEncontradaException("Categoría no encontrada con ID: " + productoDTO.getCategoriaId()));
                producto.setCategoria(categoria);
            }
        } else {
            producto.setCategoria(null);
        }

        return productoRepository.save(producto);
    }

    @Transactional
    public void eliminarProducto(Long id) {
        if (!productoRepository.existsById(id)) {
            throw new ProductoNoEncontradoException("No se puede eliminar. Producto no encontrado con ID: " + id);
        }
        productoRepository.deleteById(id);
    }
}
