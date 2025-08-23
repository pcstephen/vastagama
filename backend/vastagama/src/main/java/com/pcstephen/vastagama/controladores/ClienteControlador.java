package com.pcstephen.vastagama.controladores;

import com.pcstephen.vastagama.dto.ClienteDTO;
import com.pcstephen.vastagama.entidades.Cliente;
import com.pcstephen.vastagama.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
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

    @GetMapping("/{cod}")
    public ResponseEntity<Optional<ClienteDTO>> buscarPorCodigo(@PathVariable String cod){
        Optional<ClienteDTO> clienteDTO = service.buscarPorCodigoPublico(cod);
        if(clienteDTO.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(clienteDTO, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ClienteDTO> salvar(@RequestBody ClienteDTO clienteDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.cadastrarCliente(clienteDTO));
//        return ResponseEntity.status(HttpStatus.CREATED).body("Cliente cadastrado com sucesso!");
    }

    @PutMapping("/{codPublico}")
    public ResponseEntity<ClienteDTO> atualizar(@PathVariable String codPublico, @RequestBody ClienteDTO dto) {
        return ResponseEntity.ok(service.editarCliente(codPublico, dto));
    }
}
