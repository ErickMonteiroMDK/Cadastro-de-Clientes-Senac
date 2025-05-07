package com.senac.CadastroClienteSenac.DTOs.ClienteDTO;

import com.senac.CadastroClienteSenac.DTOs.EnderecoDTO.EnderecoResponseDTO;
import com.senac.CadastroClienteSenac.Enum.Genero;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class ClienteResponseDTO {

    private Long id;
    private String documento;
    private String nome;
    private String sobrenome;
    private String email;
    private Integer ddd;
    private Integer telefone;
    private Genero genero;
    private LocalDate dataNascimento;
    private Integer idade;
    private List<EnderecoResponseDTO> enderecos;
}
