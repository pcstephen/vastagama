package com.pcstephen.vastagama.infra.security;

import com.pcstephen.vastagama.entidades.Usuario;
import com.pcstephen.vastagama.infra.security.userdetails.UserDetailsImpl;
import com.pcstephen.vastagama.repositorios.UsuarioRepositorio;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

@Component
public class FiltroAutenticacaoUsuario extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    private String getToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.replace("Bearer ", "").trim();
        }
        return null;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if (isEndpointNaoPublico(request)) {
            String token = getToken(request);
            if (token != null) {
                try {
                    String subject = tokenService.getSubjectFromToken(token);
                    Optional<Usuario> optionalUsuario = usuarioRepositorio.findByEmail(subject);
                    if (optionalUsuario.isPresent()) {
                        Usuario usuario = optionalUsuario.get();
                        UserDetailsImpl userDetails = new UserDetailsImpl(usuario);
                        Authentication authentication = new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities());
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    } else {
                        throw new RuntimeException("Usuário não encontrado.");
                    }
                } catch (Exception e) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.getWriter().write("Token inválido ou expirado.");
                    return;
                }
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Token ausente.");
                return;
            }
        }
        filterChain.doFilter(request, response);
    }



    private boolean isEndpointNaoPublico(HttpServletRequest request){
        String requestURI = request.getRequestURI();
        return !Arrays.asList(SecurityConfiguration.ENDPOINTS_COM_AUTENTICACAO_NAO_NECESSARIA).contains(requestURI);
    }


}
