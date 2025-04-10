package com.pcstephen.vastagama.infra.excecoes;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(ObjetoNaoEncontradoException.class)
    public ResponseEntity<ErroResposta> objetoNaoEncontradoHandler(ObjetoNaoEncontradoException exception){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErroResposta(HttpStatus.NOT_FOUND, exception.getMessage()));
    }

    @ExceptionHandler(ObjetoInvalidoException.class)
    public ResponseEntity<ErroResposta> objetoInvalidoHandler(ObjetoInvalidoException exception){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErroResposta(HttpStatus.BAD_REQUEST, exception.getMessage()));
    }

}
