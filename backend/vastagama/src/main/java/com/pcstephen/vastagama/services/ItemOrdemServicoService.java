package com.pcstephen.vastagama.services;

import com.pcstephen.vastagama.entidades.ItemOrdemServico;
import com.pcstephen.vastagama.infra.excecoes.ObjetoInvalidoException;
import com.pcstephen.vastagama.repositorios.ItemOrdemServicoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class ItemOrdemServicoService {
    @Autowired
    private ItemOrdemServicoRepositorio repo;

    public Optional<ItemOrdemServico> buscarPorId(UUID id) {
        return repo.findById(id);
    }

    public void cadastrarItem(ItemOrdemServico item) {

        item.setId(UUID.randomUUID());

        confereDados(item);
        repo.save(item);
    }

    public void editarItem(UUID id, ItemOrdemServico item) {
        ItemOrdemServico novoItem = buscarPorId(id).orElseThrow(() -> new ObjetoInvalidoException("Erro: Item não encontrado para editar!"));

        novoItem.setDescricao(item.getDescricao());
        novoItem.setQuantidade(item.getQuantidade());
        novoItem.setValorUnitario(item.getValorUnitario());
        novoItem.getSubTotal();

        confereDados(novoItem);

        repo.save(novoItem);
    }

    public void deletarItem(UUID id) {
        repo.findById(id).orElseThrow(() -> new ObjetoInvalidoException("Erro: Ordem de Servico não encontrada!"));
    }

    private void confereDados(ItemOrdemServico item) {
       if(item.getDescricao() == null || item.getDescricao().isBlank() || item.getDescricao().isEmpty()){
           throw new ObjetoInvalidoException("Erro: Item sem descrição!");
       } else if(item.getQuantidade() < 0 || item.getQuantidade() == 0 ){
           throw new ObjetoInvalidoException("Erro: Quantidade inválida!");
       } else if(item.getValorUnitario() < 0 || item.getValorUnitario() == 0 ){
           throw new ObjetoInvalidoException("Erro: Valor inválido!");
       }
    }


}
