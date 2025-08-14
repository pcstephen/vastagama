//package com.pcstephen.vastagama.controladores;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import com.pcstephen.vastagama.dto.UsuarioDTO;
//import com.pcstephen.vastagama.services.UsuarioService;
//
//@RestController
//@RequestMapping("usuarios")
//public class UsuarioController {
//    @Autowired
//    UsuarioService service;
//
//    @PostMapping
//    public ResponseEntity<String> cadastrarUsuario(@RequestBody UsuarioDTO user){
//        service.cadastrarUsuario(user);
//        return  ResponseEntity.status(HttpStatus.CREATED).body("Usuário criado com sucesso!");
//    }
//
//}
