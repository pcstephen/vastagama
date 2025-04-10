package com.pcstephen.vastagama.infra.excecoes;

public class ObjetoNaoEncontradoException extends RuntimeException{
    public ObjetoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}
