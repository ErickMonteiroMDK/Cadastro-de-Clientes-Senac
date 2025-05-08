package com.senac.CadastroClienteSenac.Controller;

import com.senac.CadastroClienteSenac.DTOs.PedidoDTO.CriarPedidoDTO;
import com.senac.CadastroClienteSenac.DTOs.PedidoDTO.PedidoDTO;
import com.senac.CadastroClienteSenac.Service.PedidoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping
    public ResponseEntity<PedidoDTO> criarPedido(@Valid @RequestBody CriarPedidoDTO dto) {
        PedidoDTO pedido = pedidoService.criarPedido(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(pedido);
    }

    @DeleteMapping("/{pedidoId}/itens/{itemId}")
    public ResponseEntity<Void> removerItem(
            @PathVariable Long pedidoId,
            @PathVariable Long itemId) {
        pedidoService.removerItemDoPedido(pedidoId, itemId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{pedidoId}/endereco")
    public ResponseEntity<PedidoDTO> atualizarEndereco(
            @PathVariable Long pedidoId,
            @RequestParam Long enderecoId) {
        PedidoDTO pedido = pedidoService.atualizarEndereco(pedidoId, enderecoId);
        return ResponseEntity.ok(pedido);
    }
}
