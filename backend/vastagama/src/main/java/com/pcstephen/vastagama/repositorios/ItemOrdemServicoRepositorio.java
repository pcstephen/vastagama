package com.pcstephen.vastagama.repositorios;

import com.pcstephen.vastagama.entidades.ItemOrdemServico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ItemOrdemServicoRepositorio extends JpaRepository<ItemOrdemServico, UUID> {
    Optional<ItemOrdemServico> findById(UUID id);
}
