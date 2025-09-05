package com.pcstephen.vastagama.services;

import com.pcstephen.vastagama.dto.ItemOrdemServicoDTO;
import com.pcstephen.vastagama.dto.OrdemServicoDTO;
import com.pcstephen.vastagama.entidades.Cliente;
import com.pcstephen.vastagama.entidades.ItemOrdemServico;
import com.pcstephen.vastagama.entidades.OrdemServico;
import com.pcstephen.vastagama.infra.excecoes.ObjetoInvalidoException;
import com.pcstephen.vastagama.infra.excecoes.ObjetoNaoEncontradoException;
import com.pcstephen.vastagama.repositorios.OrdemServicoRepositorio;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrdemServicoService {
    @Autowired
    private OrdemServicoRepositorio repo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ItemOrdemServicoService itemOrdemServicoService;


    public OrdemServicoDTO buscarPorId(UUID uuid) {
        OrdemServico ordemServico = repo.findById(uuid).orElseThrow(()-> new ObjetoNaoEncontradoException("Ordem de serviço não encontrada com ID: " + uuid));
        return modelMapper.map(ordemServico, OrdemServicoDTO.class);
    }

    public OrdemServicoDTO cadastrarOrdemServico(OrdemServicoDTO ordemServicoDTO){
        Optional.ofNullable(ordemServicoDTO).orElseThrow(()-> new IllegalArgumentException("Erro: Ordem de Servico nula!"));
        OrdemServico ordemServico = modelMapper.map(ordemServicoDTO, OrdemServico.class);
        ordemServico.setId(UUID.randomUUID());

        ordemServico.setCliente(modelMapper.map(ordemServicoDTO.getCliente(), Cliente.class));

        repo.save(ordemServico);

        return modelMapper.map(ordemServico, OrdemServicoDTO.class);
    }

    public OrdemServicoDTO editarOrdemServico(UUID id, OrdemServicoDTO ordemServicoDTO){
        OrdemServico novaOrdemServico =  repo.findById(id).orElseThrow(()-> new ObjetoNaoEncontradoException("Erro: Ordem de Serviço não encontrada!"));

        novaOrdemServico.setCliente(modelMapper.map(ordemServicoDTO.getCliente(), Cliente.class));

        repo.save(novaOrdemServico);

        for(ItemOrdemServicoDTO itemOrdemServico : ordemServicoDTO.getItens()){
            itemOrdemServicoService.cadastrarItem(itemOrdemServico);
        }

        return modelMapper.map(novaOrdemServico, OrdemServicoDTO.class);
    }
    public void excluirOrdemServico(UUID id){
        repo.findById(id).orElseThrow(() -> new ObjetoNaoEncontradoException("Erro: Ordem de Serviço não encontrada!"));

        repo.deleteById(id);
    }
}



