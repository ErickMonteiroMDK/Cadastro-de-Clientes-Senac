package com.senac.CadastroClienteSenac.DTOs.PedidoDTO;

import com.senac.CadastroClienteSenac.DTOs.PedidoItemDTO.CriarPedidoItemDTO;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CriarPedidoDTO {

    @NotNull(message = "Cliente é obrigatório")
    private Long clienteId;

    @NotNull(message = "Endereço é obrigatório")
    private Long enderecoId;

    @NotEmpty(message = "Pedido deve ter pelo menos um item")
    private List<CriarPedidoItemDTO> itens;
}
