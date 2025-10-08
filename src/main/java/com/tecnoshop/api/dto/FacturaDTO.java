package com.tecnoshop.api.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public class FacturaDTO {

    @NotNull(message = "El ID del cliente no puede ser nulo.")
    private Long clienteId;

    @NotEmpty(message = "La factura debe tener al menos un detalle.")
    @Valid
    private List<FacturaDetalleDTO> detalles;

    // Getters y Setters
    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public List<FacturaDetalleDTO> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<FacturaDetalleDTO> detalles) {
        this.detalles = detalles;
    }
}
