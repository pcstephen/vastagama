package com.pcstephen.vastagama.services;

import com.pcstephen.vastagama.dto.ClienteDTO;
import com.pcstephen.vastagama.entidades.Cliente;
import com.pcstephen.vastagama.entidades.Endereco;
import com.pcstephen.vastagama.infra.excecoes.ObjetoInvalidoException;
import com.pcstephen.vastagama.repositorios.ClienteRepositorio;
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
        return repo.findAllByOrderByNomeAsc();
    }

    public List<Cliente> buscarPorNome(String nome){
        return repo.findClienteByNomeContainingIgnoreCaseOrderByNomeAsc(nome);
    }

    public Optional<Cliente> buscarPorCodigoPublico(String codigoPublico) {
        if(codigoPublico.isBlank()){
            throw new ObjetoInvalidoException("Erro: Código inválido!");
        }

        return repo.findClienteByCodigoPublico(codigoPublico);
    }

    @Transactional
    public void cadastrarCliente(Cliente cliente){
        Optional.ofNullable(cliente)
                .orElseThrow(() -> new ObjetoInvalidoException("Erro: cliente nulo!"));

            cliente.setId(UUID.randomUUID());
            cliente.gerarCodigoPublico();
            System.out.println("Código cliente: " + cliente.getCodigoPublico());


        if (cliente.getNome() == null || cliente.getNome().trim().isBlank()) {
            throw new ObjetoInvalidoException("Erro: Nome do cliente não pode ser vazio.");
        }

        repo.save(cliente);
    }

    @Transactional
    public void editarCliente(UUID id, ClienteDTO dto){
        Cliente clienteEditado = buscarPorId(id).orElseThrow(()-> new ObjetoInvalidoException("Erro: Cliente não encontrado!"));

        if(dto.nome() == null || dto.nome().trim().isBlank()){
            throw new ObjetoInvalidoException("Erro: Nome do cliente não pode ser vazio!");
        }
        clienteEditado.setNome(dto.nome());
        repo.save(clienteEditado);
    }


}
