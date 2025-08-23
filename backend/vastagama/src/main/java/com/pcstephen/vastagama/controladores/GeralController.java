package com.pcstephen.vastagama.controladores;

import com.pcstephen.vastagama.dto.ClienteDTO;
import com.pcstephen.vastagama.entidades.*;
import com.pcstephen.vastagama.infra.excecoes.ObjetoNaoEncontradoException;
import com.pcstephen.vastagama.services.*;

import java.util.Optional;
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
    private ItemOrdemServicoService itemOrdemServicoService;
    @Autowired
    private OrdemServicoService ordemServicoService;

    @PostMapping
    public ResponseEntity<ClienteDTO> cadastrarNovoCliente(@RequestBody ClienteDTO clienteDTO){
        return ResponseEntity.ok(clienteService.cadastrarCliente(clienteDTO)) ;
    }
}
