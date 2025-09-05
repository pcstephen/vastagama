package com.pcstephen.vastagama.services;

import com.pcstephen.vastagama.dto.ItemOrdemServicoDTO;
import com.pcstephen.vastagama.entidades.ItemOrdemServico;
import com.pcstephen.vastagama.infra.excecoes.ObjetoInvalidoException;
import com.pcstephen.vastagama.repositorios.ItemOrdemServicoRepositorio;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class ItemOrdemServicoService {
    @Autowired
    private ItemOrdemServicoRepositorio repo;
    @Autowired
    private ModelMapper modelMapper;

    public Optional<ItemOrdemServico> buscarPorId(UUID id) {
        return repo.findById(id);
    }

    public void cadastrarItem(ItemOrdemServicoDTO itemDTO) {
      Optional<ItemOrdemServico> item = buscarPorId(itemDTO.getId());
        if(item.isEmpty()){
          confereDados(itemDTO);
          repo.save(modelMapper.map(itemDTO, ItemOrdemServico.class));
        }
    }

    public void deletarItem(UUID id) {
        repo.findById(id).orElseThrow(() -> new ObjetoInvalidoException("Erro: Ordem de Servico não encontrada!"));
    }

    private void confereDados(ItemOrdemServicoDTO item) {
       if(item.getDescricao() == null || item.getDescricao().isBlank() || item.getDescricao().isEmpty()){
           throw new ObjetoInvalidoException("Erro: Item sem descrição!");
       } else if(item.getQuantidade() < 0 || item.getQuantidade() == 0 ){
           throw new ObjetoInvalidoException("Erro: Quantidade inválida!");
       } else if(item.getValorUnitario() < 0 || item.getValorUnitario() == 0 ){
           throw new ObjetoInvalidoException("Erro: Valor inválido!");
       }
    }


}
