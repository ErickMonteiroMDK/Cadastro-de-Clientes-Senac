package com.senac.CadastroClienteSenac.DTOs.PedidoDTO;

import com.senac.CadastroClienteSenac.DTOs.PedidoItemDTO.PedidoItemDTO;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class PedidoDTO {

    private Long id;
    private LocalDateTime dataCriacao;
    private Double valorTotal;
    private Long clienteId;
    private Long enderecoId;
    private List<PedidoItemDTO> itens;
}
