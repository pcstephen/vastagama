package com.pcstephen.vastagama.repositorios;

import com.pcstephen.vastagama.entidades.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClienteRepositorio extends JpaRepository<Cliente, UUID> {
    List<Cliente> findAllByOrderByNomeAsc();
    Optional<Cliente> findById(UUID id);
    List<Cliente> findClienteByNomeContainingIgnoreCaseOrderByNomeAsc(String nome);
    Optional<Cliente> findClienteByCodigoPublico(String codigo);
}
