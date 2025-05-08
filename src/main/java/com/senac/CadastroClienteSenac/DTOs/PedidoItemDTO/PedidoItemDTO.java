package com.senac.CadastroClienteSenac.DTOs.PedidoItemDTO;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;



@Data
@NoArgsConstructor
@AllArgsConstructor

public class PedidoItemDTO {

    private Long id;
    private Integer quantidade;
    private Double valorUnitario;
    private Long produtoId;
} 