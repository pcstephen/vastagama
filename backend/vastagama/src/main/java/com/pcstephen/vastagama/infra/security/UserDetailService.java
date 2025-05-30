package com.pcstephen.vastagama.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.pcstephen.vastagama.entidades.Usuario;
import com.pcstephen.vastagama.repositorios.UsuarioRepositorio;

@Service
public class UserDetailService implements UserDetailsService{

    @Autowired
    private UsuarioRepositorio repo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = repo.findByUsername(username);

        if(usuario == null){
            throw new UsernameNotFoundException("Usuário não encontrado!");
        }
     
     
     
        return usuario;
    }
    
}
