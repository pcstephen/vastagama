package com.pcstephen.vastagama.controladores;

import com.pcstephen.vastagama.entidades.*;
import com.pcstephen.vastagama.services.*;
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
}
