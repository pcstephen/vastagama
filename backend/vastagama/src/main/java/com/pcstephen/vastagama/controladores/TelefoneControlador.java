package com.pcstephen.vastagama.controladores;

import com.pcstephen.vastagama.entidades.Telefone;
import com.pcstephen.vastagama.services.TelefoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/telefones")
@CrossOrigin("*")
public class TelefoneControlador {

    @Autowired
    private TelefoneService service;

    @GetMapping
    private ResponseEntity<List<Telefone>> findAll(){
        List<Telefone> telefones = service.findAll();
        return new ResponseEntity<>(telefones, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> salvar(@RequestBody Telefone telefone) {
        service.cadastrarTelefone(telefone);
        return ResponseEntity.status(HttpStatus.CREATED).body("Telefone cadastrado com sucesso!");
    }

    // @PutMapping("/{id}")
    // public ResponseEntity<String> atualizar(@PathVariable UUID id,@RequestBody Telefone telefone) {

    //     service.editarTelefone(id ,telefone);
    //     return ResponseEntity.ok("Telefone editado com Sucesso!");
    // }
}
