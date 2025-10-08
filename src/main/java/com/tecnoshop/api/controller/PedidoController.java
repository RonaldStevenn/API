package com.tecnoshop.api.controller;

import com.tecnoshop.api.dto.PedidoDTO;
import com.tecnoshop.api.entity.Pedido;
import com.tecnoshop.api.service.PedidoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @GetMapping
    public ResponseEntity<List<Pedido>> getAllPedidos() {
        return ResponseEntity.ok(pedidoService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> getPedidoById(@PathVariable Long id) {
        return ResponseEntity.ok(pedidoService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<Pedido> createPedido(@Valid @RequestBody PedidoDTO pedidoDTO) {
        Pedido nuevoPedido = pedidoService.crearPedido(pedidoDTO);
        return new ResponseEntity<>(nuevoPedido, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Pedido> updatePedido(@PathVariable Long id, @Valid @RequestBody PedidoDTO pedidoDTO) {
        Pedido pedidoActualizado = pedidoService.actualizarPedido(id, pedidoDTO);
        return ResponseEntity.ok(pedidoActualizado);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletePedido(@PathVariable Long id) {
        pedidoService.eliminarPedido(id);
        return ResponseEntity.noContent().build();
    }
}
