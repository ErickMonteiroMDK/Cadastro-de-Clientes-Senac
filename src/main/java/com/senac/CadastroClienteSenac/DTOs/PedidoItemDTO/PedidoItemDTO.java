package com.senac.CadastroClienteSenac.DTOs.PedidoItemDTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PedidoItemDTO {

    private Long id;
    private Double quantidade;
    private Double valorUnitario;
    private Long produtoId;
}
