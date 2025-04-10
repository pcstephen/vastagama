package com.pcstephen.vastagama.services;

import com.pcstephen.vastagama.entidades.ItemOrdemServico;
import com.pcstephen.vastagama.entidades.OrdemServico;
import com.pcstephen.vastagama.infra.excecoes.ObjetoInvalidoException;
import com.pcstephen.vastagama.infra.excecoes.ObjetoNaoEncontradoException;
import com.pcstephen.vastagama.repositorios.OrdemServicoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrdemServicoService {
    @Autowired
    private OrdemServicoRepositorio repo;

    public Optional<OrdemServico> buscarPorId(UUID uuid) {
        return repo.findById(uuid);
    }

    public void cadastrarOrdemServico(OrdemServico ordemServico){
        Optional.ofNullable(ordemServico).orElseThrow(()-> new IllegalArgumentException("Erro: Ordem de Servico nula!"));
        ordemServico.setId(UUID.randomUUID());

        if(ordemServico.getDataCadastro() == null){
            LocalDate dataCadastro = LocalDate.now();
            ordemServico.setDataCadastro(dataCadastro);
        }

        if(ordemServico.getCliente() == null){
            throw new ObjetoInvalidoException("Erro: Ordem de Servico sem cliente vinculado!");
        }

        repo.save(ordemServico);
    }

    public void editarOrdemServico(UUID id,OrdemServico ordemServico){
        repo.findById(id).orElseThrow(()-> new ObjetoNaoEncontradoException("Erro: Ordem de Serviço não encontrada!"));

        OrdemServico novaOrdemServico = new OrdemServico();

        novaOrdemServico.setCliente(ordemServico.getCliente());
        novaOrdemServico.setDataCadastro(ordemServico.getDataCadastro());
        novaOrdemServico.setObservacoes(ordemServico.getObservacoes());
        for(ItemOrdemServico itemOrdemServico : ordemServico.getItens()){
            novaOrdemServico.adicionaItem(itemOrdemServico);
        }
        repo.save(novaOrdemServico);
    }
    public void excluirOrdemServico(UUID id){
        repo.findById(id).orElseThrow(() -> new ObjetoNaoEncontradoException("Erro: Ordem de Serviço não encontrada!"));

        repo.deleteById(id);
    }
}



