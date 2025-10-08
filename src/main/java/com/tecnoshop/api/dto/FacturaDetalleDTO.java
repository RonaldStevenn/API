package com.tecnoshop.api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class FacturaDetalleDTO {

    @NotNull(message = "El ID del producto no puede ser nulo.")
    private Long productoId;

    @NotNull(message = "La cantidad no puede ser nula.")
    @Positive(message = "La cantidad debe ser un número positivo.")
    private Integer cantidad;

    // Getters y Setters
    public Long getProductoId() {
        return productoId;
    }

    public void setProductoId(Long productoId) {
        this.productoId = productoId;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }
}
