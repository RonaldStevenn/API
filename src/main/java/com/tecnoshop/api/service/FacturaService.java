package com.tecnoshop.api.service;

import com.tecnoshop.api.dto.FacturaDTO;
import com.tecnoshop.api.dto.FacturaDetalleDTO;
import com.tecnoshop.api.entity.Cliente;
import com.tecnoshop.api.entity.Factura;
import com.tecnoshop.api.entity.FacturaDetalle;
import com.tecnoshop.api.entity.Producto;
import com.tecnoshop.api.exception.RecursoNoEncontradoException;
import com.tecnoshop.api.exception.StockInsuficienteException;
import com.tecnoshop.api.repository.ClienteRepository;
import com.tecnoshop.api.repository.FacturaRepository;
import com.tecnoshop.api.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FacturaService {

    @Autowired
    private FacturaRepository facturaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Transactional(readOnly = true)
    public List<Factura> obtenerTodasLasFacturas() {
        return facturaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Factura> obtenerFacturaPorId(Long id) {
        return facturaRepository.findById(id);
    }

    @Transactional
    public Factura crearFactura(FacturaDTO facturaDTO) {
        Cliente cliente = clienteRepository.findById(facturaDTO.getClienteId())
                .orElseThrow(() -> new RecursoNoEncontradoException("Cliente no encontrado con ID: " + facturaDTO.getClienteId()));

        Factura nuevaFactura = new Factura();
        nuevaFactura.setCliente(cliente);
        nuevaFactura.setFechaEmision(Instant.now());

        List<FacturaDetalle> detalles = facturaDTO.getDetalles().stream().map(detalleDTO -> {
            Producto producto = productoRepository.findById(detalleDTO.getProductoId())
                    .orElseThrow(() -> new RecursoNoEncontradoException("Producto no encontrado con ID: " + detalleDTO.getProductoId()));

            if (producto.getStock() < detalleDTO.getCantidad()) {
                throw new StockInsuficienteException("Stock insuficiente para el producto: " + producto.getNombre());
            }

            producto.setStock(producto.getStock() - detalleDTO.getCantidad());
            
            FacturaDetalle detalle = new FacturaDetalle();
            detalle.setFactura(nuevaFactura);
            detalle.setProducto(producto);
            detalle.setCantidad(detalleDTO.getCantidad());
            detalle.setPrecioUnitario(producto.getPrecio());
            return detalle;
        }).collect(Collectors.toList());

        nuevaFactura.setDetalles(detalles);

        // Calcular subtotal, IVA y total
        BigDecimal subtotal = BigDecimal.ZERO;
        for (FacturaDetalle detalle : nuevaFactura.getDetalles()) {
            BigDecimal precioProducto = detalle.getPrecioUnitario();
            BigDecimal cantidad = new BigDecimal(detalle.getCantidad());
            subtotal = subtotal.add(precioProducto.multiply(cantidad));
        }

        BigDecimal iva = subtotal.multiply(new BigDecimal("0.21"));
        BigDecimal total = subtotal.add(iva);

        nuevaFactura.setSubtotal(subtotal);
        nuevaFactura.setIva(iva);
        nuevaFactura.setTotal(total);

        // Guardar la factura y sus detalles
        return facturaRepository.save(nuevaFactura);
    }
}
