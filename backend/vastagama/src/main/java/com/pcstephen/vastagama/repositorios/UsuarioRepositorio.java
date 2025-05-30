package com.pcstephen.vastagama.repositorios;

import com.pcstephen.vastagama.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, UUID> {
    Usuario findByUsername(String username);
}
