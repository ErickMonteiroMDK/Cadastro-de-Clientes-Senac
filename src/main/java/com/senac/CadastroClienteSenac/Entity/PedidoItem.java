package com.senac.CadastroClienteSenac.Entity;

import jakarta.persistence.*;

@Entity(name = "pedido_item")
public class PedidoItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "produto_id")
    private Produto produto;

    private Integer quantidade;

    private Double valorUnitario;

    public Long getId() {
        return id;
    }

    public Produto getProduto() {
        return produto;
    }

    public PedidoItem setProduto(Produto produto) {
        this.produto = produto;
        return this;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public PedidoItem setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
        return this;
    }

    public Double getValorUnitario() {
        return valorUnitario;
    }

    public PedidoItem setValorUnitario(Double valorUnitario) {
        this.valorUnitario = valorUnitario;
        return this;
    }
}