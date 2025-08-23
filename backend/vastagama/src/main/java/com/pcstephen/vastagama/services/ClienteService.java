package com.pcstephen.vastagama.services;

import com.pcstephen.vastagama.dto.ClienteDTO;
import com.pcstephen.vastagama.entidades.Cliente;
import com.pcstephen.vastagama.infra.excecoes.ObjetoInvalidoException;
import com.pcstephen.vastagama.infra.excecoes.ObjetoNaoEncontradoException;
import com.pcstephen.vastagama.repositorios.ClienteRepositorio;
import org.modelmapper.ModelMapper;
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
    @Autowired
    private OrdemServicoService ordemServicoService;

    ModelMapper modelMapper;

    public Optional<Cliente> buscarPorId(UUID id) {
        return repo.findById(id);
    }

    public List<Cliente> findAll() {
        return repo.findAllByOrderByNomeAsc();
    }

    public List<Cliente> buscarPorNome(String nome){
        return repo.findClienteByNomeContainingIgnoreCaseOrderByNomeAsc(nome);
    }

    public Optional<ClienteDTO> buscarPorCodigoPublico(String codigoPublico) {
        if(codigoPublico.isBlank()){
            throw new ObjetoInvalidoException("Erro: Código inválido!");
        }

        return repo.findClienteByCodigoPublico(codigoPublico).
                map(cliente -> modelMapper.map(cliente, ClienteDTO.class));
    }

    @Transactional
    public ClienteDTO cadastrarCliente(ClienteDTO clienteDTO){

        if(clienteDTO == null){
            throw new ObjetoInvalidoException("Erro ao salvar cliente! Verifique dados enviados");
        }
        Cliente salvo = repo.save(modelMapper.map(clienteDTO, Cliente.class));
        clienteDTO.getOrdemServico().forEach(
                ordemServico -> {
                    ordemServico.setCliente(salvo);
                    ordemServicoService.cadastrarOrdemServico(ordemServico);
                }
        );


        return modelMapper.map(salvo, ClienteDTO.class);
    }

    @Transactional
    public ClienteDTO editarCliente(String codPub, ClienteDTO dto){
        Cliente clienteExistente = buscarPorCodigoPublico(codPub).map(
                c -> modelMapper.map(c, Cliente.class)).orElseThrow(()-> new ObjetoNaoEncontradoException("Erro ao editar cliente! Id inválido!")
        );

        modelMapper.map(dto, clienteExistente);
        return modelMapper.map(repo.save(clienteExistente), ClienteDTO.class);
    }


}
