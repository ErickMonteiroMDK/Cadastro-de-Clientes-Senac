package com.senac.CadastroClienteSenac.Entity;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import com.senac.CadastroClienteSenac.Enum.Genero;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@Entity(name = "cliente")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "O documento não pode ser nulo.")
    @Column(name = "documento", nullable = false, length = 100)
    private String documento;

    @NotNull(message = "O nome não pode ser nulo.")
    @Column(name = "nome", nullable = false, length = 50)
    private String nome;

    @NotNull(message = "O sobrenome não pode ser nulo.")
    @Column(name = "sobrenome", nullable = false, length = 100)
    private String sobrenome;

    @NotNull(message = "O e-mail não pode ser nulo.")
    @Column(name = "email", nullable = false, length = 150)
    private String email;

    @NotNull(message = "O DDD não pode ser nulo.")
    @Column(name = "ddd", nullable = false)
    private int ddd;

    @NotNull(message = "O telefone não pode ser nulo.")
    @Column(name = "telefone", nullable = false)
    private int telefone;

    @NotNull(message = "O genero não pode ser nulo.")
    @Column(name = "genero", nullable = false)
    private Genero genero;

    @NotNull(message = "A data de nascimento não pode ser nulo.")
    @Column(name = "data_nascimento", nullable = false)
    private LocalDate dataNascimento;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    @Size(max = 3, message = "Cliente não pode ter mais que 3 endereços")
    private List<Endereco> enderecos;

    //O metodo calcula a idade do cliente para retornar no JSON porém não salva no banco.
    @Transient
    public int getIdade() {
        return Period.between(this.dataNascimento, LocalDate.now()).getYears();
    }

}
