package com.pcstephen.vastagama.services;

import com.pcstephen.vastagama.dto.EnderecoDTO;
import com.pcstephen.vastagama.entidades.Endereco;
import com.pcstephen.vastagama.infra.excecoes.ObjetoInvalidoException;
import com.pcstephen.vastagama.repositorios.EnderecoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EnderecoService {

    @Autowired
    private EnderecoRepositorio repo;

    public List<Endereco> findAll(){
        return repo.findAll();
    }

    public Optional<Endereco> buscarPorId(UUID id) {
        return repo.findById(id);
    }

    @Transactional
    public void cadastrar(Endereco endereco){
        endereco.setId(UUID.randomUUID());
        confereDados(endereco);
        repo.save(endereco);
    }

    @Transactional
    public void atualizar(UUID id, EnderecoDTO enderecoDTO){
        Endereco enderecoEditado = buscarPorId(id).orElseThrow(()-> new ObjetoInvalidoException("Endereco não encontrado!"));
        enderecoEditado.setRua(enderecoDTO.rua());
        enderecoEditado.setRua(enderecoDTO.bairro());
        enderecoEditado.setRua(enderecoDTO.complemento());
        enderecoEditado.setRua(enderecoDTO.cidade());

        confereDados(enderecoEditado);
        repo.save(enderecoEditado);
    }

    @Transactional
    public void remover(UUID id){
        buscarPorId(id).orElseThrow(()-> new ObjetoInvalidoException("Endereço não encontrado!"));
        repo.deleteById(id);
    }

    private void confereDados(Endereco endereco){
        if(endereco.getCidade() == null || endereco.getCidade().isEmpty() || endereco.getCidade().isBlank()){
            throw new ObjetoInvalidoException("Campo cidade deve ser preenchido!");
        }if(endereco.getRua() == null || endereco.getRua().isEmpty() || endereco.getRua().isBlank()){
            throw new ObjetoInvalidoException("Campo RUA deve ser preenchido!");
        }if(endereco.getBairro() == null || endereco.getBairro().isEmpty() || endereco.getBairro().isBlank()){
            throw new ObjetoInvalidoException("Campo BAIRRO deve ser preenchido!");
        }if(endereco.getComplemento() == null || endereco.getComplemento().isEmpty() || endereco.getComplemento().isBlank()){
            throw new ObjetoInvalidoException("Campo COMPLEMENTO deve ser preenchido!");
        }if(endereco.getCliente() == null){
            throw new ObjetoInvalidoException("Endereco sem cliente vinculado!");
        }
    }
}
