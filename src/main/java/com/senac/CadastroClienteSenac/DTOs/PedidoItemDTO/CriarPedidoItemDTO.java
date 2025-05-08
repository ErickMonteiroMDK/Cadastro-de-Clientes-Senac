package com.senac.CadastroClienteSenac.DTOs.PedidoItemDTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CriarPedidoItemDTO {

    @NotNull(message = "Produto é obrigatório")
    private Long produtoId;

    @NotNull(message = "Quantidade é obrigatória")
    @Positive(message = "Quantidade deve ser positiva")
    private Double quantidade;

    public Long getProdutoId() {
        return produtoId;
    }

    public CriarPedidoItemDTO setProdutoId(Long produtoId) {
        this.produtoId = produtoId;
        return this;
    }

    public Double getQuantidade() {
        return quantidade;
    }

    public CriarPedidoItemDTO setQuantidade(Double quantidade) {
        this.quantidade = quantidade;
        return this;
    }
}