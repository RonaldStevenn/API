package com.tecnoshop.api.controller;

import com.tecnoshop.api.dto.ClienteDTO;
import com.tecnoshop.api.entity.Cliente;
import com.tecnoshop.api.exception.ClienteNoEncontradoException;
import com.tecnoshop.api.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public ResponseEntity<Page<Cliente>> getAllClientes(Pageable pageable) {
        Page<Cliente> clientes = clienteService.obtenerTodosLosClientes(pageable);
        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> getClienteById(@PathVariable Long id) {
        return clienteService.obtenerClientePorId(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ClienteNoEncontradoException("Cliente no encontrado con ID: " + id));
    }

    @PostMapping
    public ResponseEntity<Cliente> createCliente(@Valid @RequestBody ClienteDTO clienteDTO) {
        Cliente nuevoCliente = clienteService.crearCliente(clienteDTO);
        return new ResponseEntity<>(nuevoCliente, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> updateCliente(@PathVariable Long id, @Valid @RequestBody ClienteDTO clienteDTO) {
        Cliente clienteActualizado = clienteService.actualizarCliente(id, clienteDTO);
        return ResponseEntity.ok(clienteActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCliente(@PathVariable Long id) {
        clienteService.eliminarCliente(id);
        return ResponseEntity.noContent().build();
    }
}
