package com.tecnoshop.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CategoriaDTO {

    @NotBlank(message = "El nombre de la categoría no puede estar vacío.")
    @Size(max = 100, message = "El nombre no puede tener más de 100 caracteres.")
    private String nombre;

    // Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
