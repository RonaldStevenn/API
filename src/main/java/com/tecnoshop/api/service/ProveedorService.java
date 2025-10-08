package com.tecnoshop.api.service;

import com.tecnoshop.api.dto.ProveedorDTO;
import com.tecnoshop.api.entity.Proveedor;
import com.tecnoshop.api.exception.RecursoNoEncontradoException;
import com.tecnoshop.api.repository.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProveedorService {

    @Autowired
    private ProveedorRepository proveedorRepository;

    @Transactional(readOnly = true)
    public List<Proveedor> obtenerTodos() {
        return proveedorRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Proveedor obtenerPorId(Long id) {
        return proveedorRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Proveedor no encontrado con ID: " + id));
    }

    @Transactional
    public Proveedor crearProveedor(ProveedorDTO proveedorDTO) {
        Proveedor proveedor = new Proveedor();
        proveedor.setNombre(proveedorDTO.getNombre());
        proveedor.setNumeroContacto(proveedorDTO.getNumeroContacto());
        return proveedorRepository.save(proveedor);
    }

    @Transactional
    public Proveedor actualizarProveedor(Long id, ProveedorDTO proveedorDTO) {
        Proveedor proveedor = obtenerPorId(id);
        proveedor.setNombre(proveedorDTO.getNombre());
        proveedor.setNumeroContacto(proveedorDTO.getNumeroContacto());
        return proveedorRepository.save(proveedor);
    }

    @Transactional
    public void eliminarProveedor(Long id) {
        Proveedor proveedor = obtenerPorId(id);
        // Considerar la lógica de negocio: ¿qué pasa con los productos si se elimina un proveedor?
        // Por ahora, solo eliminamos el proveedor. La relación se deshará automáticamente.
        proveedorRepository.delete(proveedor);
    }
}
