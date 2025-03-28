package com.pcstephen.vastagama.controladores;

import com.pcstephen.vastagama.entidades.ItemOrdemServico;
import com.pcstephen.vastagama.services.ItemOrdemServicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("itens")
@CrossOrigin("*")
public class ItemOrdemServiceControlador {
    @Autowired
    private ItemOrdemServicoService service;


    @GetMapping("/{id}")
    public ResponseEntity<Optional<ItemOrdemServico>> getItemOrdemServico(@PathVariable UUID id) {
        Optional<ItemOrdemServico> item =  service.buscarPorId(id);
        return  ResponseEntity.status(HttpStatus.OK).body(item);

    }

    @PostMapping
    public ResponseEntity<String> salvar(@RequestBody ItemOrdemServico item) {
        service.cadastrarItem(item);
        return ResponseEntity.status(HttpStatus.CREATED).body("Item cadastrado com sucesso!");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> atualizar(@PathVariable UUID id, @RequestBody ItemOrdemServico item) {
        service.editarItem(id, item);
        return ResponseEntity.status(HttpStatus.OK).body("Item atualizado com sucesso!");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(UUID id){
        service.deletarItem(id);
        return ResponseEntity.noContent().build();
    }
}
