package com.tecnoshop.api.controller;

import com.tecnoshop.api.dto.FacturaDTO;
import com.tecnoshop.api.entity.Factura;
import com.tecnoshop.api.service.FacturaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/facturas")
public class FacturaController {

    @Autowired
    private FacturaService facturaService;

    @GetMapping
    public ResponseEntity<List<Factura>> getAllFacturas() {
        List<Factura> facturas = facturaService.obtenerTodasLasFacturas();
        return ResponseEntity.ok(facturas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Factura> getFacturaById(@PathVariable Long id) {
        return facturaService.obtenerFacturaPorId(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Factura no encontrada con ID: " + id));
    }

    @PostMapping
    public ResponseEntity<Factura> createFactura(@Valid @RequestBody FacturaDTO facturaDTO) {
        Factura nuevaFactura = facturaService.crearFactura(facturaDTO);
        return new ResponseEntity<>(nuevaFactura, HttpStatus.CREATED);
    }
}
