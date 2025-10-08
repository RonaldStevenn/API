package com.tecnoshop.api.controller;

import com.tecnoshop.api.dto.ProveedorDTO;
import com.tecnoshop.api.entity.Proveedor;
import com.tecnoshop.api.service.ProveedorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/proveedores")
public class ProveedorController {

    @Autowired
    private ProveedorService proveedorService;

    @GetMapping
    public ResponseEntity<List<Proveedor>> getAllProveedores() {
        return ResponseEntity.ok(proveedorService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Proveedor> getProveedorById(@PathVariable Long id) {
        return ResponseEntity.ok(proveedorService.obtenerPorId(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Proveedor> createProveedor(@Valid @RequestBody ProveedorDTO proveedorDTO) {
        Proveedor nuevoProveedor = proveedorService.crearProveedor(proveedorDTO);
        return new ResponseEntity<>(nuevoProveedor, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Proveedor> updateProveedor(@PathVariable Long id, @Valid @RequestBody ProveedorDTO proveedorDTO) {
        Proveedor proveedorActualizado = proveedorService.actualizarProveedor(id, proveedorDTO);
        return ResponseEntity.ok(proveedorActualizado);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteProveedor(@PathVariable Long id) {
        proveedorService.eliminarProveedor(id);
        return ResponseEntity.noContent().build();
    }
}
