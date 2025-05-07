package com.senac.CadastroClienteSenac.Exeception;

public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }
}
