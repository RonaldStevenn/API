package com.tecnoshop.api.service;

import com.tecnoshop.api.dto.PedidoDTO;
import com.tecnoshop.api.dto.PedidoDetalleDTO;
import com.tecnoshop.api.entity.*;
import com.tecnoshop.api.exception.RecursoNoEncontradoException;
import com.tecnoshop.api.repository.ClienteRepository;
import com.tecnoshop.api.repository.PedidoRepository;
import com.tecnoshop.api.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Transactional(readOnly = true)
    public List<Pedido> obtenerTodos() {
        return pedidoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Pedido obtenerPorId(Long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Pedido no encontrado con ID: " + id));
    }

    @Transactional
    public Pedido crearPedido(PedidoDTO pedidoDTO) {
        Cliente cliente = clienteRepository.findById(pedidoDTO.getClienteId())
                .orElseThrow(() -> new RecursoNoEncontradoException("Cliente no encontrado con ID: " + pedidoDTO.getClienteId()));

        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setFechaPedido(Instant.now());
        pedido.setEstado(pedidoDTO.getEstado());
        pedido.setDireccionEnvio(pedidoDTO.getDireccionEnvio());

        List<PedidoDetalle> detalles = pedidoDTO.getDetalles().stream().map(detalleDTO -> {
            Producto producto = productoRepository.findById(detalleDTO.getProductoId())
                    .orElseThrow(() -> new RecursoNoEncontradoException("Producto no encontrado con ID: " + detalleDTO.getProductoId()));

            PedidoDetalle detalle = new PedidoDetalle();
            detalle.setPedido(pedido);
            detalle.setProducto(producto);
            detalle.setCantidad(detalleDTO.getCantidad());
            detalle.setPrecioUnitario(producto.getPrecio()); // Guardar el precio al momento del pedido
            return detalle;
        }).collect(Collectors.toList());

        pedido.setDetalles(detalles);
        return pedidoRepository.save(pedido);
    }

    @Transactional
    public Pedido actualizarPedido(Long id, PedidoDTO pedidoDTO) {
        Pedido pedido = obtenerPorId(id);
        
        Cliente cliente = clienteRepository.findById(pedidoDTO.getClienteId())
                .orElseThrow(() -> new RecursoNoEncontradoException("Cliente no encontrado con ID: " + pedidoDTO.getClienteId()));

        pedido.setCliente(cliente);
        pedido.setEstado(pedidoDTO.getEstado());
        pedido.setDireccionEnvio(pedidoDTO.getDireccionEnvio());

        // Para una actualización real, se podría necesitar una lógica más compleja
        // para manejar los detalles (añadir, eliminar, modificar).
        // Por simplicidad, aquí reemplazamos los detalles.
        pedido.getDetalles().clear();
        List<PedidoDetalle> nuevosDetalles = pedidoDTO.getDetalles().stream().map(detalleDTO -> {
            Producto producto = productoRepository.findById(detalleDTO.getProductoId())
                    .orElseThrow(() -> new RecursoNoEncontradoException("Producto no encontrado con ID: " + detalleDTO.getProductoId()));
            
            PedidoDetalle detalle = new PedidoDetalle();
            detalle.setPedido(pedido);
            detalle.setProducto(producto);
            detalle.setCantidad(detalleDTO.getCantidad());
            detalle.setPrecioUnitario(producto.getPrecio());
            return detalle;
        }).collect(Collectors.toList());
        pedido.getDetalles().addAll(nuevosDetalles);

        return pedidoRepository.save(pedido);
    }

    @Transactional
    public void eliminarPedido(Long id) {
        Pedido pedido = obtenerPorId(id);
        pedidoRepository.delete(pedido);
    }
}
