package com.tecnoshop.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class ClienteDTO {

    @NotBlank(message = "El nombre no puede estar vacío.")
    @Size(max = 100, message = "El nombre no puede tener más de 100 caracteres.")
    private String nombre;

    @NotBlank(message = "El apellido no puede estar vacío.")
    @Size(max = 100, message = "El apellido no puede tener más de 100 caracteres.")
    private String apellido;

    @NotBlank(message = "El email no puede estar vacío.")
    @Email(message = "El formato del email no es válido.")
    @Size(max = 255, message = "El email no puede tener más de 255 caracteres.")
    private String email;

    @Size(max = 20, message = "El teléfono no puede tener más de 20 caracteres.")
    @Pattern(regexp = "^[0-9+\\-]+$", message = "El teléfono solo puede contener números y los símbolos + -")
    private String telefono;

    @NotBlank
    private String direccion;

    private String pais;

    private String codigoPostal;

    // Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }
}
