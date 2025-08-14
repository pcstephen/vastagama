package com.pcstephen.vastagama.controladores;

import com.pcstephen.vastagama.dto.CriarUsuarioDTO;
import com.pcstephen.vastagama.dto.JWTTokenDTO;
import com.pcstephen.vastagama.dto.LoginRequest;
import com.pcstephen.vastagama.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class LoginController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/login")
    public ResponseEntity<JWTTokenDTO> atenticarUsuario(@RequestBody LoginRequest loginRequest){
        JWTTokenDTO jwtTokenDTO = usuarioService.autenticarUsuario(loginRequest);
        return ResponseEntity.ok(jwtTokenDTO);
    }

    @PostMapping
    public ResponseEntity<Void> criarUsuario(@RequestBody CriarUsuarioDTO criarUsuarioDTO){
        usuarioService.criarUsuario(criarUsuarioDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/teste")
    public ResponseEntity<String> autenticar() {
        return new ResponseEntity<>("Autenticado com sucesso", HttpStatus.OK);
    }

    @GetMapping("/teste/user")
    public ResponseEntity<String> autenticarUsuario() {
        return new ResponseEntity<>("Cliente autenticado com sucesso", HttpStatus.OK);
    }

    @GetMapping("/teste/adm")
    public ResponseEntity<String> autenticarAdm() {
        return new ResponseEntity<>("Administrador autenticado com sucesso", HttpStatus.OK);
    }
}
