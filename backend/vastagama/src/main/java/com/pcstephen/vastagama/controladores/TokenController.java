package com.pcstephen.vastagama.controladores;

import com.pcstephen.vastagama.dto.LoginRequest;
import com.pcstephen.vastagama.dto.LoginResponse;
import com.pcstephen.vastagama.entidades.Usuario;
import com.pcstephen.vastagama.repositorios.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.Optional;

@RestController()
public class TokenController {
    private final JwtEncoder jwtEncoder;

    @Autowired
    private UsuarioRepositorio userRepository;

    public TokenController(JwtEncoder jwtEncoder) {
        this.jwtEncoder = jwtEncoder;
    }

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        Optional<Usuario> user = userRepository.findByUsername(loginRequest.username());

        if (user.isEmpty() || !user.get().isLoginCorrect(loginRequest, bCryptPasswordEncoder)) {
            throw new BadCredentialsException("Invalid username or password");
        }

        var instantNow = Instant.now();
        Long expiresIn = 300L;
        var claims = JwtClaimsSet.builder()
                .issuer("MyBackEnd")
                .subject(user.get().getId().toString())
                .expiresAt(instantNow.plusSeconds(expiresIn))
                .issuedAt(instantNow)
                .build();

        var jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims));

        return ResponseEntity.ok(new LoginResponse(jwtValue.getTokenValue(), expiresIn));
    }
}
