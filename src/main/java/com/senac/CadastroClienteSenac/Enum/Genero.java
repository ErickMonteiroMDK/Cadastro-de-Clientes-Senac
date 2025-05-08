package com.senac.CadastroClienteSenac.Enum;

import com.fasterxml.jackson.annotation.JsonValue;

//Enum para o genero do cliente. Talvez fazer mudança para suportar o identificador no JSON
public enum Genero {

    MASCULINO("Masculino"),
    FEMININO("Feminino"),
    NAO_BINARIO("Não Binário"),
    AGENERO("Agênero"),
    TRANSGENERO_FEMININO("Transgênero Feminino"),
    TRANSGENERO_MASCULINO("Transgênero Masculino"),
    OUTROS("Outros");

    private final String descricao;

    Genero(String descricao) {
        this.descricao = descricao;
    }

    @JsonValue
    public String getDescricao() {
        return descricao;
    }
}
