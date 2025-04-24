package com.pcstephen.vastagama.repositorios;

import com.pcstephen.vastagama.entidades.OrdemServico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrdemServicoRepositorio extends JpaRepository<OrdemServico, UUID> {
    Optional<OrdemServico> findById(UUID id);
    List<OrdemServico> findOrdemServicoByClienteId(UUID id);
}
