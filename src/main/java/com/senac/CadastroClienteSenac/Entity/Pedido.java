package com.senac.CadastroClienteSenac.Entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "pedido")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "endereco_id")
    private Endereco endereco;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PedidoItem> itens = new ArrayList<>();

    private Double valorTotal;

    private LocalDateTime dataCriacao;

    @PrePersist
    public void prePersist() {
        this.dataCriacao = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public Pedido setId(Long id) {
        this.id = id;
        return this;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Pedido setCliente(Cliente cliente) {
        this.cliente = cliente;
        return this;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public Pedido setEndereco(Endereco endereco) {
        this.endereco = endereco;
        return this;
    }

    public List<PedidoItem> getItens() {
        return itens;
    }

    public Pedido addItem(PedidoItem item) {
        this.itens.add(item);
        return this;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public Pedido setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
        return this;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public Pedido setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
        return this;
    }

    public void calcularEAtualizarValorTotal() {
        this.valorTotal = itens.stream()
                .mapToDouble(item -> item.getQuantidade() * item.getValorUnitario())
                .sum();
    }
}