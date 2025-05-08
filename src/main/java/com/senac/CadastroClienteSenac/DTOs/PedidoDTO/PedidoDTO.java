package com.senac.CadastroClienteSenac.DTOs.PedidoDTO;

import com.senac.CadastroClienteSenac.DTOs.PedidoItemDTO.PedidoItemDTO;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PedidoDTO {

    private Long id;
    private LocalDateTime dataCriacao;
    private Double valorTotal;
    private Long clienteId;
    private Long enderecoId;
    private List<PedidoItemDTO> itens;

    public PedidoDTO() {
    }

    public PedidoDTO(Long id, LocalDateTime dataCriacao, Double valorTotal, Long clienteId, Long enderecoId, List<PedidoItemDTO> itens) {
        this.id = id;
        this.dataCriacao = dataCriacao;
        this.valorTotal = valorTotal;
        this.clienteId = clienteId;
        this.enderecoId = enderecoId;
        this.itens = itens;
    }
}