package com.senac.CadastroClienteSenac.DTOs.ClienteDTO;

import com.senac.CadastroClienteSenac.Enum.Genero;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public class ClienteAtualizarDTO {

    @NotNull(message = "O ID não pode ser nulo.")
    private Long id;

    @Size(min = 11, max = 14, message = "O documento deve ter entre 11 e 14 caracteres.")
    private String documento;

    @Size(min = 2, max = 50, message = "O nome deve ter entre 2 e 50 caracteres.")
    @Pattern(regexp = "^[a-zA-ZÀ-ÿ\\s]+$", message = "O nome deve conter apenas letras.")
    private String nome;

    @Size(min = 2, max = 100, message = "O sobrenome deve ter entre 2 e 100 caracteres.")
    @Pattern(regexp = "^[a-zA-ZÀ-ÿ\\s]+$", message = "O sobrenome deve conter apenas letras.")
    private String sobrenome;

    @Email(message = "O e-mail deve ser válido.")
    @Size(max = 150, message = "O e-mail não pode exceder 150 caracteres.")
    private String email;

    @Min(value = 11, message = "DDD inválido.")
    @Max(value = 99, message = "DDD inválido.")
    private Integer ddd;

    @Min(value = 10000000, message = "Telefone inválido.")
    @Max(value = 999999999, message = "Telefone inválido.")
    private Integer telefone;

    private Genero genero;

    @Past(message = "A data de nascimento deve estar no passado.")
    private LocalDate dataNascimento;
}
