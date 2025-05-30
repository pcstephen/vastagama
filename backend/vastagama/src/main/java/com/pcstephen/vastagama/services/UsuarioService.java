package com.pcstephen.vastagama.services;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pcstephen.vastagama.dto.UsuarioDTO;
import com.pcstephen.vastagama.entidades.Role;
import com.pcstephen.vastagama.entidades.Usuario;
import com.pcstephen.vastagama.infra.excecoes.ObjetoInvalidoException;
import com.pcstephen.vastagama.repositorios.RoleRepository;
import com.pcstephen.vastagama.repositorios.UsuarioRepositorio;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepositorio repo;
    @Autowired
    private RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    
    @Transactional
    public Usuario cadastrarUsuario(UsuarioDTO usuarioDTO){

        Role basicRole = roleRepository.findByName(Role.values.BASIC.name());

        if(usuarioDTO.getUsername().trim().isBlank()){
            throw new ObjetoInvalidoException("Nome inválido!");
        } if(usuarioDTO.getPassword().isBlank()){
            throw new ObjetoInvalidoException("Senha inválida!");
        }
        Usuario existente = repo.findByUsername(usuarioDTO.getUsername().trim());
        
        if (existente != null) {
            throw new ObjetoInvalidoException("Nome de usuário já em uso!");
        } else {
            Usuario novoUsuario = new Usuario();            
            if(novoUsuario.getId() == null){
                novoUsuario.setId(UUID.randomUUID());
            }
            novoUsuario.setUsername(usuarioDTO.getUsername());
            novoUsuario.setPassword(passwordEncoder.encode(usuarioDTO.getPassword()));
            return repo.save(novoUsuario);
        }

    }
    
}
