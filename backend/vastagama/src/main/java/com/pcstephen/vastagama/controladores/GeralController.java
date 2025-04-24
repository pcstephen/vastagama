package com.pcstephen.vastagama.controladores;

import com.pcstephen.vastagama.dto.ClienteDTO;
import com.pcstephen.vastagama.dto.TelefoneDTO;
import com.pcstephen.vastagama.entidades.*;
import com.pcstephen.vastagama.infra.excecoes.ObjetoInvalidoException;
import com.pcstephen.vastagama.infra.excecoes.ObjetoNaoEncontradoException;
import com.pcstephen.vastagama.services.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/geral")
@CrossOrigin("*")
public class GeralController {

    @Autowired
    private ClienteService clienteService;
    @Autowired
    private EnderecoService enderecoService;
    @Autowired
    private TelefoneService telefoneService;
    @Autowired
    private ItemOrdemServicoService itemOrdemServicoService;
    @Autowired
    private OrdemServicoService ordemServicoService;


    @PostMapping
    @Transactional
    public ResponseEntity<Cliente> cadastroNovo(@RequestBody Cliente cliente){
        Cliente novoCliente = new Cliente();
        novoCliente.setNome(cliente.getNome());
        clienteService.cadastrarCliente(novoCliente);

        Endereco endereco = new Endereco();
        endereco.setRua(cliente.getEndereco().getRua());
        endereco.setBairro(cliente.getEndereco().getBairro());
        endereco.setComplemento(cliente.getEndereco().getComplemento());
        endereco.setCidade(cliente.getEndereco().getCidade());
        endereco.setCliente(novoCliente);

        enderecoService.cadastrar(endereco);
        novoCliente.setEndereco(endereco);

        for(int i=0; i<cliente.getTelefones().size(); i++){
            Telefone telefone = new Telefone();
            telefone.setNumero(cliente.getTelefones().get(i).getNumero());
            telefone.setCliente(novoCliente);

            novoCliente.getTelefones().add(telefone);
            telefoneService.cadastrarTelefone(telefone);
        }

        for(OrdemServico ordemServico : cliente.getOrdemDeServicos()) {
            OrdemServico novaOrdemServico = new OrdemServico();
            novaOrdemServico.setObservacoes(ordemServico.getObservacoes());
            novaOrdemServico.setCliente(novoCliente);

            novoCliente.adicionaOrdemServico(novaOrdemServico);

            for (ItemOrdemServico item : ordemServico.getItens()) {
                ItemOrdemServico novoItem = new ItemOrdemServico();
                novoItem.setQuantidade(item.getQuantidade());
                novoItem.setDescricao(item.getDescricao());
                novoItem.setValorUnitario(item.getValorUnitario());
                novoItem.setOrdemServico(novaOrdemServico);

                itemOrdemServicoService.cadastrarItem(novoItem);
                novaOrdemServico.adicionaItem(novoItem);

            }

            ordemServicoService.cadastrarOrdemServico(novaOrdemServico);
        }

        return new ResponseEntity<>(novoCliente, HttpStatus.CREATED);
    }


    @Transactional
    @PatchMapping("/{id}")
    public ResponseEntity<Cliente> editarCliente(@PathVariable UUID id, @RequestBody ClienteDTO dto){
        Optional<Cliente> clienteEditado = clienteService.buscarPorId(id);

        if (clienteEditado.isEmpty()) {
            throw new ObjetoNaoEncontradoException("Erro: Cliente não encontrado!");
        }

        Cliente cliente = clienteEditado.get();
        clienteService.editarCliente(id, dto);

        if (dto.enderecoDTO() != null) {
            Endereco enderecoAtualizado = enderecoService.atualizar(dto.enderecoDTO().id(), dto.enderecoDTO());
            cliente.setEndereco(enderecoAtualizado);
        } else throw new ObjetoInvalidoException("Erro: Endereco não pode ser nulo!");

        if(dto.telefonesDTO() != null){
            for (TelefoneDTO telefone : dto.telefonesDTO()) {
                Telefone telefoneEditado = telefoneService.editarTelefone(telefone.id(), telefone);
                telefoneEditado.setCliente(cliente);
            }
        } else throw new ObjetoInvalidoException("Erro: Lista de telefones não pode ser nula ou vazia.");
        
        return  ResponseEntity.ok().body(cliente);
    }
}
