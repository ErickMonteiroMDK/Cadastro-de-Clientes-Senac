package com.senac.CadastroClienteSenac.Exeception;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ErrorResponse {

    private int status;
    private String titulo;
    private String mensagem;
    private LocalDateTime timestamp;

    public ErrorResponse(int status, String titulo, String mensagem) {
        this.status = status;
        this.titulo = titulo;
        this.mensagem = mensagem;
        this.timestamp = LocalDateTime.now();
    }

}
