package com.pcstephen.vastagama.repositorios;

import com.pcstephen.vastagama.entidades.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EnderecoRepositorio extends JpaRepository<Endereco, UUID> {
    Optional<Endereco> findById(UUID id);
    void deleteById(UUID id);
}
