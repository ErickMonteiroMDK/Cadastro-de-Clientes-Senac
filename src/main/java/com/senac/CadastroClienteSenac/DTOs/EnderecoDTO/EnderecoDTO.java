package com.senac.CadastroClienteSenac.DTOs.EnderecoDTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EnderecoDTO {

    private Long id;
    private String logradouro;
    private String bairro;
    private String numero;
    private String cidade;
    private String estado;
    private String cep;
    private Long clienteId;

}
