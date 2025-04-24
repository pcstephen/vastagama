package com.pcstephen.vastagama.repositorios;

import com.pcstephen.vastagama.entidades.Telefone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TelefoneRepositorio extends JpaRepository<Telefone, UUID> {
    Optional<Telefone> findById(UUID id);
}
