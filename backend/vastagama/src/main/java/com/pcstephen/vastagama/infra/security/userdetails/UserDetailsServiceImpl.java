package com.pcstephen.vastagama.infra.security.userdetails;

import com.pcstephen.vastagama.entidades.Usuario;
import com.pcstephen.vastagama.repositorios.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsuarioRepositorio repo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = repo.findByEmail(username).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        return new UserDetailsImpl(usuario);
    }

}
