package com.tecnoshop.api.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "clientes")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "apellido", nullable = false, length = 100)
    private String apellido;

    @Column(name = "email", nullable = false, unique = true, length = 255)
    private String email;

    @Column(name = "telefono", length = 20)
    private String telefono;

    @Column(name = "direccion", columnDefinition = "TEXT")
    private String direccion;

    @Column(length = 100)
    private String pais;

    @Column(name = "codigo_postal", length = 20)
    private String codigoPostal;

    @JsonManagedReference("cliente-factura")
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Factura> facturas = new ArrayList<>();

    @JsonManagedReference("cliente-pedido")
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pedido> pedidos = new ArrayList<>();

    @Column(name = "fecha_registro", nullable = false, updatable = false)
    private Instant fechaRegistro;

    @PrePersist
    protected void onCreate() {
        fechaRegistro = Instant.now();
    }

    // Getters y Setters (sin cambios)
    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }

    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }

    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getTelefono() { return telefono; }

    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getDireccion() { return direccion; }

    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getPais() { return pais; }

    public void setPais(String pais) { this.pais = pais; }

    public String getCodigoPostal() { return codigoPostal; }

    public void setCodigoPostal(String codigoPostal) { this.codigoPostal = codigoPostal; }

    public List<Factura> getFacturas() { return facturas; }

    public void setFacturas(List<Factura> facturas) { this.facturas = facturas; }

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

    public Instant getFechaRegistro() { return fechaRegistro; }
    
    public void setFechaRegistro(Instant fechaRegistro) { this.fechaRegistro = fechaRegistro; }
}