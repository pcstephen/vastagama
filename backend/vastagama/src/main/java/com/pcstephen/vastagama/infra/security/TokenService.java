package com.pcstephen.vastagama.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.pcstephen.vastagama.infra.security.userdetails.UserDetailsImpl;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Service
public class TokenService {

    private static final String SECRET_KEY = "5l!lK;/4Y4jP1l6LoikGu@gC/~:zK^8r";
    private static final String ISSUER = "vastagama";

    public String gerarTokenJWT(UserDetailsImpl usuario){
        try{
            System.out.println(usuario.getUsername());
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);  // Define o algoritmo para criar assinatura do token
            return JWT.create()
                    .withIssuer(ISSUER) // Define o emissor
                    .withIssuedAt(dataCriacao())
                    .withExpiresAt(dataExpiracao())
                    .withSubject(usuario.getUsername()) // Define o assunto do token (neste caso, o nome de usuário)
                    .sign(algorithm); // Assina o token usando o algoritmo especificado

        } catch (JWTCreationException e) {
            throw new JWTCreationException("Erro ao gerar token.", e);
        }
    }

    public String getSubjectFromToken(String token){
        try{
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY); // Define o algoritmo para verificar assinatura do token
            return JWT.require(algorithm)
                    .withIssuer(ISSUER)
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception){
            throw new JWTVerificationException("Token inválido ou expirado.");
        }
    }

    private Instant dataCriacao(){
        return ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")).toInstant();
    }

    private Instant dataExpiracao(){
        return ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")).plusHours(4).toInstant();
    }
}
