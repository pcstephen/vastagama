package com.pcstephen.vastagama.controladores;

import com.pcstephen.vastagama.dto.OrdemServicoDTO;
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
    public ResponseEntity<OrdemServicoDTO> getOrdemServico(@PathVariable UUID id) {
        OrdemServicoDTO ordemServico =  service.buscarPorId(id);
        return  ResponseEntity.status(HttpStatus.OK).body(ordemServico);
    }

    @PostMapping
    public ResponseEntity<OrdemServicoDTO> salvar(@RequestBody OrdemServicoDTO ordemServicoDTO) {
        service.cadastrarOrdemServico(ordemServicoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(ordemServicoDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> atualizar(@PathVariable UUID id, @RequestBody OrdemServicoDTO ordemServicoDTO) {
        service.editarOrdemServico(id, ordemServicoDTO);
        return ResponseEntity.status(HttpStatus.OK).body("Ordem de Serviço atualizada com sucesso!");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(UUID id){
        service.excluirOrdemServico(id);
        return ResponseEntity.noContent().build();
    }
}
