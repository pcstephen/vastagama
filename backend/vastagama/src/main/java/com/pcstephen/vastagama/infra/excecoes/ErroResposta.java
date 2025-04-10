package com.pcstephen.vastagama.infra.excecoes;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import java.time.LocalDateTime;

@Getter
@Setter
public class ErroResposta {
    private HttpStatus status;
    private String erro;
    private LocalDateTime timestamp;

    public ErroResposta() {
    }

    public ErroResposta(HttpStatus status, String erro) {
        this.status = status;
        this.erro = erro;
        this.timestamp = LocalDateTime.now();
    }
}
