package com.tecnoshop.api.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.math.BigDecimal; // <-- IMPORT AÑADIDO
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "facturas")
public class Factura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonBackReference("cliente-factura")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @Column(name = "fecha_emision", nullable = false, updatable = false)
    private Instant fechaEmision;

    @JsonManagedReference("factura-detalle")
    @OneToMany(mappedBy = "factura", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FacturaDetalle> detalles = new ArrayList<>();

    @Column(precision = 10, scale = 2)
    private BigDecimal subtotal;

    @Column(precision = 10, scale = 2)
    private BigDecimal iva;

    @Column(precision = 10, scale = 2)
    private BigDecimal total;

    @PrePersist
    protected void onCreate() {
        fechaEmision = Instant.now();
    }
    
    // Getters y Setters (sin cambios)
    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Cliente getCliente() { return cliente; }

    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    public Instant getFechaEmision() { return fechaEmision; }

    public void setFechaEmision(Instant fechaEmision) { this.fechaEmision = fechaEmision; }

    public List<FacturaDetalle> getDetalles() { return detalles; }

    public void setDetalles(List<FacturaDetalle> detalles) { this.detalles = detalles; }

    public BigDecimal getSubtotal() { return subtotal; }

    public void setSubtotal(BigDecimal subtotal) { this.subtotal = subtotal; }

    public BigDecimal getIva() { return iva; }

    public void setIva(BigDecimal iva) { this.iva = iva; }

    public BigDecimal getTotal() { return total; }

    public void setTotal(BigDecimal total) { this.total = total; }
    
    
    // Método helper para añadir detalles
    public void addDetalle(FacturaDetalle detalle) {
        detalles.add(detalle);
        detalle.setFactura(this);
    }
}