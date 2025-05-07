package com.senac.CadastroClienteSenac.DTOs.ClienteDTO;

import com.senac.CadastroClienteSenac.Enum.Genero;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ClienteRequestDTO {

    @Size(min = 3, message = "O nome para busca deve ter pelo menos 3 caracteres.")
    private String nome;

    @Size(min = 11, max = 14, message = "O documento deve ter entre 11 e 14 caracteres.")
    private String documento;

    @Email(message = "O e-mail deve ser válido.")
    private String email;

    private Genero genero;

    @PastOrPresent(message = "A data de nascimento deve estar no passado ou presente.")
    private LocalDate dataNascimentoInicio;

    @PastOrPresent(message = "A data de nascimento deve estar no passado ou presente.")
    private LocalDate dataNascimentoFim;

    @Min(value = 0, message = "Idade mínima inválida.")
    @Max(value = 150, message = "Idade máxima inválida.")
    private Integer idadeMinima;

    @Min(value = 0, message = "Idade mínima inválida.")
    @Max(value = 150, message = "Idade máxima inválida.")
    private Integer idadeMaxima;

}
