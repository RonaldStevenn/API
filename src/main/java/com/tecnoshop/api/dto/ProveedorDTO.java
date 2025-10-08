package com.tecnoshop.api.dto;

import jakarta.validation.constraints.NotBlank;

public class ProveedorDTO {

    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;

    private String numeroContacto;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNumeroContacto() {
        return numeroContacto;
    }

    public void setNumeroContacto(String numeroContacto) {
        this.numeroContacto = numeroContacto;
    }
}
