package com.senac.CadastroClienteSenac.DTOs.PedidoDTO;

import com.senac.CadastroClienteSenac.DTOs.PedidoItemDTO.PedidoItemDTO;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Data
public class PedidoDTO {

    private Long id;
    private LocalDateTime dataCriacao;
    private Double valorTotal;
    private Long clienteId;
    private Long enderecoId;
    private List<PedidoItemDTO> itens = new ArrayList<>();

    public void setItens(List<PedidoItemDTO> itens) {
        this.itens.clear();
        if (itens != null) {
            this.itens.addAll(itens);
        }
    }

} 