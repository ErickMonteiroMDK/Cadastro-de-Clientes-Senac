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

    public Long getClienteId() {
        return clienteId;
    }

    public CriarPedidoDTO setClienteId(Long clienteId) {
        this.clienteId = clienteId;
        return this;
    }

    public Long getEnderecoId() {
        return enderecoId;
    }

    public CriarPedidoDTO setEnderecoId(Long enderecoId) {
        this.enderecoId = enderecoId;
        return this;
    }

    public List<CriarPedidoItemDTO> getItens() {
        return itens;
    }

    public CriarPedidoDTO setItens(List<CriarPedidoItemDTO> itens) {
        this.itens = itens;
        return this;
    }
}