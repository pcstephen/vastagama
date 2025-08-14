package com.pcstephen.vastagama.services;

import com.pcstephen.vastagama.dto.CriarUsuarioDTO;
import com.pcstephen.vastagama.dto.JWTTokenDTO;
import com.pcstephen.vastagama.dto.LoginRequest;
import com.pcstephen.vastagama.entidades.Role;
import com.pcstephen.vastagama.entidades.Usuario;
import com.pcstephen.vastagama.infra.security.SecurityConfiguration;
import com.pcstephen.vastagama.infra.security.TokenService;
import com.pcstephen.vastagama.infra.security.userdetails.UserDetailsImpl;
import com.pcstephen.vastagama.repositorios.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService jwtTokenService;

    @Autowired
    private UsuarioRepositorio userRepository;

    @Autowired
    private SecurityConfiguration securityConfiguration;

    // Método responsável por autenticar um usuário e retornar um token JWT
    public JWTTokenDTO autenticarUsuario(LoginRequest loginRequest){
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password());

        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return new JWTTokenDTO(jwtTokenService.gerarTokenJWT(userDetails));
    }

    public void criarUsuario(CriarUsuarioDTO criarUsuarioDTO){
        Usuario usuario = Usuario.builder().
        email(criarUsuarioDTO.email()).
                username(criarUsuarioDTO.username())
                .password(securityConfiguration.bCryptPasswordEncoder().encode(criarUsuarioDTO.password()))
                .roles(java.util.List.of(Role.builder().name(criarUsuarioDTO.role()).build())).build();

        userRepository.save(usuario);
    }
}
