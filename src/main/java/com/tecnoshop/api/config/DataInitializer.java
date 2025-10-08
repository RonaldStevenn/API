package com.tecnoshop.api.config;

import com.tecnoshop.api.entity.Rol;
import com.tecnoshop.api.entity.Usuario;
import com.tecnoshop.api.repository.RolRepository;
import com.tecnoshop.api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Crear roles si no existen
        if (rolRepository.findByNombre("ROLE_USER").isEmpty()) {
            Rol userRole = new Rol();
            userRole.setNombre("ROLE_USER");
            rolRepository.save(userRole);
        }
        if (rolRepository.findByNombre("ROLE_ADMIN").isEmpty()) {
            Rol adminRole = new Rol();
            adminRole.setNombre("ROLE_ADMIN");
            rolRepository.save(adminRole);
        }

        // Crear usuario administrador si no existe
        if (usuarioRepository.findByUsername("admin").isEmpty()) {
            Usuario adminUser = new Usuario();
            adminUser.setUsername("admin");
            adminUser.setPassword(passwordEncoder.encode("admin123"));
            
            Rol adminRole = rolRepository.findByNombre("ROLE_ADMIN").get();
            Rol userRole = rolRepository.findByNombre("ROLE_USER").get();
            adminUser.setRoles(Set.of(adminRole, userRole));
            
            usuarioRepository.save(adminUser);
        }
    }
}
