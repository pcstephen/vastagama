package com.pcstephen.vastagama.controladores;

import com.pcstephen.vastagama.dto.ClienteDTO;
import com.pcstephen.vastagama.entidades.Cliente;
import com.pcstephen.vastagama.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/clientes")
@CrossOrigin("*")
public class ClienteControlador {

    @Autowired
    private ClienteService service;

    @GetMapping
    public ResponseEntity<List<Cliente>> listar(){
        List<Cliente> list = service.findAll();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/buscaPorNome")
    public ResponseEntity<List<Cliente>> buscarPorNome(@RequestParam String nome){
        List<Cliente> list = service.buscarPorNome(nome);
        if(list.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> salvar(@RequestBody Cliente cliente) {
        service.cadastrarCliente(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body("Cliente cadastrado com sucesso!");
    }

    @PutMapping("/{id}/nome")
    public ResponseEntity<String> atualizar(@PathVariable UUID id, @RequestBody ClienteDTO dto) {
        service.editarCliente(id, dto);
        return ResponseEntity.ok("Cliente editado com Sucesso!");
    }
}
