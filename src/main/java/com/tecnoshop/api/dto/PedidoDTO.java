package com.tecnoshop.api.dto;

import com.tecnoshop.api.entity.EstadoPedido;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public class PedidoDTO {

    @NotNull(message = "El ID del cliente no puede ser nulo")
    private Long clienteId;

    @NotNull(message = "El estado del pedido no puede ser nulo")
    private EstadoPedido estado;

    @NotEmpty(message = "La dirección de envío no puede estar vacía")
    private String direccionEnvio;

    @NotEmpty(message = "El pedido debe tener al menos un detalle")
    private List<PedidoDetalleDTO> detalles;

    // Getters y Setters
    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public EstadoPedido getEstado() {
        return estado;
    }

    public void setEstado(EstadoPedido estado) {
        this.estado = estado;
    }

    public String getDireccionEnvio() {
        return direccionEnvio;
    }

    public void setDireccionEnvio(String direccionEnvio) {
        this.direccionEnvio = direccionEnvio;
    }

    public List<PedidoDetalleDTO> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<PedidoDetalleDTO> detalles) {
        this.detalles = detalles;
    }
}
