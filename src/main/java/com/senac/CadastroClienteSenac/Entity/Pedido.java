package com.senac.CadastroClienteSenac.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "pedido")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dataCriacao = LocalDateTime.now();

    private Double valorTotal;

    @ManyToOne
    private Cliente cliente;

    @ManyToOne
    private Endereco endereco;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PedidoItem> itens = new ArrayList<>();

    public void calcularEAtualizarValorTotal() {
        if (itens.isEmpty()) {
            throw new IllegalStateException("O pedido deve conter pelo menos um item");
        }

        this.valorTotal = itens.stream()
                .mapToDouble(item -> item.getQuantidade() * item.getValorUnitario())
                .sum();

        if (this.valorTotal < 0) {
            throw new IllegalStateException("O valor total do pedido não pode ser negativo");
        }

    }

    public void addItem(PedidoItem item) {
        if (item == null) {
            throw new IllegalArgumentException("O item não pode ser nulo");
        }

        item.setPedido(this);
        this.itens.add(item);
    }


}