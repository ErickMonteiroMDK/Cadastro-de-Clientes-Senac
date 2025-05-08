package com.senac.CadastroClienteSenac.DTOs.PedidoItemDTO;

import lombok.Data;

@Data
public class PedidoItemDTO {

    private Long id;
    private Integer quantidade;
    private Double valorUnitario;
    private Long produtoId;

    public PedidoItemDTO() {
    }

    public PedidoItemDTO(Long id, Integer quantidade, Double valorUnitario, Long produtoId) {
        this.id = id;
        this.quantidade = quantidade;
        this.valorUnitario = valorUnitario;
        this.produtoId = produtoId;
    }
}