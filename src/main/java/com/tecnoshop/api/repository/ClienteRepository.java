package com.tecnoshop.api.repository;

import com.tecnoshop.api.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    // JpaRepository ya proporciona los métodos CRUD básicos:
    // save(), findById(), findAll(), deleteById(), etc.

    // Podemos añadir métodos de consulta personalizados si es necesario.
    // Por ejemplo, para buscar un cliente por su email.
    Optional<Cliente> findByEmail(String email);
}
