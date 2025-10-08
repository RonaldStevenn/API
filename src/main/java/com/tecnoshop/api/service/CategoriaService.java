package com.tecnoshop.api.service;

import com.tecnoshop.api.dto.CategoriaDTO;
import com.tecnoshop.api.entity.Categoria;
import com.tecnoshop.api.exception.CategoriaNoEncontradaException;
import com.tecnoshop.api.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Transactional(readOnly = true)
    public List<Categoria> obtenerTodasLasCategorias() {
        return categoriaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Categoria> obtenerCategoriaPorId(Long id) {
        return categoriaRepository.findById(id);
    }

    @Transactional
    public Categoria crearCategoria(CategoriaDTO categoriaDTO) {
        Categoria categoria = new Categoria();
        categoria.setNombre(categoriaDTO.getNombre());
        return categoriaRepository.save(categoria);
    }

    @Transactional
    public Categoria actualizarCategoria(Long id, CategoriaDTO categoriaDTO) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new CategoriaNoEncontradaException("Categoría no encontrada con ID: " + id));
        categoria.setNombre(categoriaDTO.getNombre());
        return categoriaRepository.save(categoria);
    }

    @Transactional
    public void eliminarCategoria(Long id) {
        if (!categoriaRepository.existsById(id)) {
            throw new CategoriaNoEncontradaException("No se puede eliminar. Categoría no encontrada con ID: " + id);
        }
        categoriaRepository.deleteById(id);
    }
}
