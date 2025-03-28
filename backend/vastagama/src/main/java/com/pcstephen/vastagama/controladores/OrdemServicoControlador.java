package com.pcstephen.vastagama.controladores;

import com.pcstephen.vastagama.entidades.ItemOrdemServico;
import com.pcstephen.vastagama.entidades.OrdemServico;
import com.pcstephen.vastagama.services.OrdemServicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("ordemServico")
@CrossOrigin("*")
public class OrdemServicoControlador {
    @Autowired
    private OrdemServicoService service;

    @GetMapping("/{id}")
    public ResponseEntity<Optional<OrdemServico>> getOrdemServico(@PathVariable UUID id) {
        Optional<OrdemServico> ordemServico =  service.buscarPorId(id);
        return  ResponseEntity.status(HttpStatus.OK).body(ordemServico);

    }

    @PostMapping
    public ResponseEntity<String> salvar(@RequestBody OrdemServico ordemServico) {
        service.cadastrarOrdemServico(ordemServico);
        return ResponseEntity.status(HttpStatus.CREATED).body("Ordem de Serviço cadastrada com sucesso!");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> atualizar(@PathVariable UUID id, @RequestBody OrdemServico ordemServico) {
        service.editarOrdemServico(id, ordemServico);
        return ResponseEntity.status(HttpStatus.OK).body("Ordem de Serviço atualizada com sucesso!");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(UUID id){
        service.excluirOrdemServico(id);
        return ResponseEntity.noContent().build();
    }
}
