package com.senac.CadastroClienteSenac.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Entity(name = "pedido_item")
public class PedidoItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "O pedido é obrigatório")
    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    @NotNull(message = "O produto é obrigatório")
    @ManyToOne
    @JoinColumn(name = "produto_id")
    private Produto produto;

    @NotNull(message = "A quantidade é obrigatória")
    @Min(value = 1, message = "A quantidade deve ser maior que zero")
    private Integer quantidade;

    @NotNull(message = "O valor unitário é obrigatório")
    @Min(value = 1, message = "A quantidade deve ser maior que zero")
    private Double valorUnitario;

    public PedidoItem(Pedido pedido, Produto produto, Integer quantidade, Double valorUnitario) {
        this.pedido = pedido;
        this.produto = produto;
        this.quantidade = quantidade;
        this.valorUnitario = valorUnitario;
    }

}
