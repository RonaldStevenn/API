package com.tecnoshop.api.service;

import com.tecnoshop.api.dto.ClienteDTO;
import com.tecnoshop.api.entity.Cliente;
import com.tecnoshop.api.exception.ClienteNoEncontradoException;
import com.tecnoshop.api.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Transactional(readOnly = true)
    public Page<Cliente> obtenerTodosLosClientes(Pageable pageable) {
        return clienteRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Optional<Cliente> obtenerClientePorId(Long id) {
        return clienteRepository.findById(id);
    }

    @Transactional
    public Cliente crearCliente(ClienteDTO clienteDTO) {
        Cliente cliente = new Cliente();
        cliente.setNombre(clienteDTO.getNombre());
        cliente.setApellido(clienteDTO.getApellido());
        cliente.setEmail(clienteDTO.getEmail());
        cliente.setTelefono(clienteDTO.getTelefono());
        cliente.setDireccion(clienteDTO.getDireccion());
        cliente.setPais(clienteDTO.getPais());
        cliente.setCodigoPostal(clienteDTO.getCodigoPostal());

        return clienteRepository.save(cliente);
    }

    @Transactional
    public Cliente actualizarCliente(Long id, ClienteDTO clienteDTO) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ClienteNoEncontradoException("Cliente no encontrado con ID: " + id));

        cliente.setNombre(clienteDTO.getNombre());
        cliente.setApellido(clienteDTO.getApellido());
        cliente.setEmail(clienteDTO.getEmail());
        cliente.setTelefono(clienteDTO.getTelefono());
        cliente.setDireccion(clienteDTO.getDireccion());
        cliente.setPais(clienteDTO.getPais());
        cliente.setCodigoPostal(clienteDTO.getCodigoPostal());

        return clienteRepository.save(cliente);
    }

    @Transactional
    public void eliminarCliente(Long id) {
        if (!clienteRepository.existsById(id)) {
            throw new ClienteNoEncontradoException("No se puede eliminar. Cliente no encontrado con ID: " + id);
        }
        clienteRepository.deleteById(id);
    }
}
