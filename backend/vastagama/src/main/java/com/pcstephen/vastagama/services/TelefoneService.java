package com.pcstephen.vastagama.services;

import com.pcstephen.vastagama.entidades.Telefone;
import com.pcstephen.vastagama.infra.excecoes.ObjetoInvalidoException;
import com.pcstephen.vastagama.repositorios.TelefoneRepositorio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TelefoneService {

    @Autowired
    private TelefoneRepositorio repo;

    public Optional<Telefone> buscarPorId(UUID id) {
        return repo.findById(id);
    }

    @Transactional
    public void cadastrarTelefone(Telefone telefone){
        telefone.setId(UUID.randomUUID());

        conferirDados(telefone);
        repo.save(telefone);
    }


    @Transactional
    public void editarTelefone(UUID id,Telefone telefone){
        Telefone telefoneEditado = buscarPorId(id).orElseThrow(()-> new ObjetoInvalidoException("Telefone não encontrado!"));
        telefoneEditado.setNumero(telefone.getNumero());

        conferirDados(telefone);
        repo.save(telefoneEditado);
    }

    @Transactional
    public void deletar(UUID id){
        buscarPorId(id).orElseThrow(()-> new ObjetoInvalidoException("Telefone não encontrado!"));

        repo.deleteById(id);
    }

    private void conferirDados(Telefone telefone) {
        if(telefone.getNumero() == null){
            throw new ObjetoInvalidoException("O campo Número deve ser preenchido!");
        } else if(telefone.getNumero().length() != 11){
            throw new ObjetoInvalidoException("Número inválido!");
        } else if(telefone.getCliente() == null){
            throw new ObjetoInvalidoException("Telefone não tem um cliente associado!");
        }
    }

    public List<Telefone> findAll() {
        return repo.findAll();
    }
}
