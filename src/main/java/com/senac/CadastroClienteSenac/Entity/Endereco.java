package com.senac.CadastroClienteSenac.Entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
@Builder(toBuilder = true)
@Entity(name = "endereco")
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Logradouro é obrigatório")
    @Length(max = 100, message = "Logradouro deve ter no máximo 100 caracteres")
    @Column(nullable = false, length = 100)
    private String logradouro;

    @NotBlank(message = "Bairro é obrigatório")
    @Length(max = 50, message = "Bairro deve ter no máximo 50 caracteres")
    @Column(nullable = false, length = 50)
    private String bairro;

    @NotBlank(message = "Número é obrigatório")
    @Length(max = 10, message = "Número deve ter no máximo 10 caracteres")
    @Column(nullable = false, length = 10)
    private String numero;

    @NotBlank(message = "Cidade é obrigatório")
    @Length(max = 50, message = "Cidade deve ter no máximo 50 caracteres")
    @Column(nullable = false, length = 50)
    private String cidade;

    @NotBlank(message = "Estado é obrigatório")
    @Length(min = 2, max = 2, message = "Estado deve ter exatamente 2 caracteres")
    @Column(nullable = false, length = 2)
    private String estado;

    @NotNull(message = "CEP é obrigatório")
    @Digits(integer = 8, fraction = 0, message = "CEP deve conter apenas números com 8 dígitos")
    @Column(nullable = false, length = 8)
    private Integer cep;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    @NotNull(message = "Cliente é obrigatório")
    private Cliente cliente;

    @Builder
    public Endereco(String logradouro, String bairro, String numero,
                    String cidade, String estado, Integer cep, Cliente cliente) {
        this.logradouro = logradouro;
        this.bairro = bairro;
        this.numero = numero;
        this.cidade = cidade;
        this.estado = estado;
        this.cep = cep;
        this.cliente = cliente;
    }

}
