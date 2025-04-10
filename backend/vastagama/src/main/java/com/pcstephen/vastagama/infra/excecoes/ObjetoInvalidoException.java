package com.pcstephen.vastagama.infra.excecoes;

public class ObjetoInvalidoException extends RuntimeException{
    public ObjetoInvalidoException(String mensagem) {
        super(mensagem);
    }
}
