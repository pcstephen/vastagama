package com.pcstephen.vastagama.services;

import com.pcstephen.vastagama.entidades.ItemOrdemServico;
import com.pcstephen.vastagama.repositorios.ItemOrdemServicoRepositorio;
import jakarta.persistence.EntityNotFoundException;
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
        if(item.getId() == null){
            UUID id = UUID.randomUUID();
            item.setId(id);
        }

        confereDados(item);
        repo.save(item);
    }

    public void editarItem(UUID id, ItemOrdemServico item) {
        ItemOrdemServico novoItem = buscarPorId(id).orElseThrow(() -> new EntityNotFoundException("Erro: Item não encontrado para editar!"));

        novoItem.setDescricao(item.getDescricao());
        novoItem.setQuantidade(item.getQuantidade());
        novoItem.setValorUnitario(item.getValorUnitario());
        novoItem.getSubTotal();

        confereDados(novoItem);

        repo.save(novoItem);
    }

    public void deletarItem(UUID id) {
        repo.findById(id).orElseThrow(() -> new EntityNotFoundException("Erro: Ordem de Servico não encontrada!"));
    }

    private void confereDados(ItemOrdemServico item) {
       if(item.getDescricao() == null || item.getDescricao().isBlank() || item.getDescricao().isEmpty()){
           throw new IllegalArgumentException("Erro: Item sem descrição!");
       } else if(item.getQuantidade() < 0 || item.getQuantidade() == 0 ){
           throw new IllegalArgumentException("Erro: Quantidade inválida!");
       } else if(item.getValorUnitario() < 0 || item.getValorUnitario() == 0 ){
           throw new IllegalArgumentException("Erro: Valor inválido!");
       }
    }


}
