package com.pcstephen.vastagama.controladores;

import com.pcstephen.vastagama.dto.EnderecoDTO;
import com.pcstephen.vastagama.entidades.Endereco;
import com.pcstephen.vastagama.services.EnderecoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/enderecos")
@CrossOrigin("*")
public class EnderecoControlador {

    @Autowired
    private EnderecoService service;

    @GetMapping
    private ResponseEntity<List<Endereco>> findAll(){
        List<Endereco> enderecos = service.findAll();
        return new ResponseEntity<>(enderecos, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> salvar(@RequestBody Endereco endereco) {
        service.cadastrar(endereco);
        return ResponseEntity.status(HttpStatus.CREATED).body("Endereco cadastrado com sucesso!");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> atualizar(@PathVariable UUID id, @RequestBody EnderecoDTO enderecoDTO) {
        service.atualizar(id, enderecoDTO);
        return ResponseEntity.ok("Endereco editado com Sucesso!");
    }
}
