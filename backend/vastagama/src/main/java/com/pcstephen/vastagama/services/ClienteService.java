package com.pcstephen.vastagama.services;

import com.pcstephen.vastagama.dto.ClienteDTO;
import com.pcstephen.vastagama.entidades.Cliente;
import com.pcstephen.vastagama.repositorios.ClienteRepositorio;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepositorio repo;

    public Optional<Cliente> buscarPorId(UUID id) {
        return repo.findById(id);
    }

    public List<Cliente> findAll() {
        return repo.findAll();
    }

    public List<Cliente> buscarPorNome(String nome){
        return repo.findClienteByNomeContainingIgnoreCaseOrderByNomeAsc(nome);
    }

    @Transactional
    public void cadastrarCliente(Cliente cliente){
        Optional.ofNullable(cliente)
                .orElseThrow(() -> new IllegalArgumentException("Erro: cliente nulo!"));

        if (cliente.getId() == null) {
            cliente.setId(UUID.randomUUID());
        }

        if (cliente.getNome() == null || cliente.getNome().trim().isBlank()) {
            throw new IllegalArgumentException("Erro: Nome do cliente não pode ser vazio.");
        }

        repo.save(cliente);
    }

    @Transactional
    public void editarCliente(UUID id, ClienteDTO dto){
        Cliente clienteEditado = buscarPorId(id).orElseThrow(()-> new EntityNotFoundException("Erro: Cliente não encontrado!"));

        if(dto.nome() == null || dto.nome().trim().isBlank()){
            throw new IllegalArgumentException("Erro: Nome do cliente não pode ser vazio!");
        }
        clienteEditado.setNome(dto.nome());
        repo.save(clienteEditado);
    }


}
