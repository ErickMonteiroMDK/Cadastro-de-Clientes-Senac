package com.senac.CadastroClienteSenac.Service;

import com.senac.CadastroClienteSenac.DTOs.PedidoDTO.CriarPedidoDTO;
import com.senac.CadastroClienteSenac.DTOs.PedidoDTO.PedidoDTO;
import com.senac.CadastroClienteSenac.DTOs.PedidoItemDTO.CriarPedidoItemDTO;
import com.senac.CadastroClienteSenac.DTOs.PedidoItemDTO.PedidoItemDTO;
import com.senac.CadastroClienteSenac.Entity.*;
import com.senac.CadastroClienteSenac.Exeception.BusinessException;
import com.senac.CadastroClienteSenac.Exeception.ResourceNotFoundException;
import com.senac.CadastroClienteSenac.Repository.PedidoRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ClienteService clienteService;
    private final EnderecoService enderecoService;
    private final ProdutoService produtoService;


    @Transactional
    public PedidoDTO criarPedido(CriarPedidoDTO dto) {
        validarCriacaoPedido(dto);
        Cliente cliente = clienteService.buscarPorId(dto.getClienteId());
        Endereco endereco = validarEnderecoCliente(dto.getEnderecoId(), cliente);


        Pedido pedido = Pedido.builder()
                .cliente(cliente)
                .endereco(endereco)
                .itens(new ArrayList<>()) // ou new ArrayList<>() se preferir List
                .dataCriacao(LocalDateTime.now())
                .valorTotal(null)
                .build();

        pedido.setCliente(cliente);
        pedido.setEndereco(endereco);
        adicionarItensAoPedido(pedido, dto.getItens());
        pedido.calcularEAtualizarValorTotal();
        pedido = pedidoRepository.save(pedido);
        return toDTO(pedido);
    }



    private void validarItemPedido(CriarPedidoItemDTO itemDto) {

        if (itemDto == null) {
            throw new BusinessException("Item do pedido não pode ser nulo");
        }

        if (itemDto.getQuantidade() <= 0) {
            throw new BusinessException("A quantidade do item deve ser maior que zero");
        }

        if (itemDto.getProdutoId() == null) {
            throw new BusinessException("O ID do produto é obrigatório");
        }
    }


    private void verificarProdutoDuplicado(Set<Long> produtosIds, Long produtoId) {
        if (!produtosIds.add(produtoId)) {
            throw new BusinessException("Produto duplicado no pedido");
        }

    }


    private void validarCriacaoPedido(CriarPedidoDTO dto) {
        if (dto.getItens() == null || dto.getItens().isEmpty()) {
            throw new BusinessException("O pedido deve conter pelo menos um item");
        }

    }


    private Endereco validarEnderecoCliente(Long enderecoId, Cliente cliente) {
        Endereco endereco = enderecoService.buscarEnderecoPorId(enderecoId);
        if (!endereco.getCliente().getId().equals(cliente.getId())) {
            throw new BusinessException("Endereço não pertence ao cliente");
        }
        return endereco;
    }


    private void adicionarItensAoPedido(Pedido pedido, List<CriarPedidoItemDTO> itensDTO) {
        Set<Long> produtosIds = new HashSet<>();

        for (CriarPedidoItemDTO itemDto : itensDTO) {
            validarItemPedido(itemDto);

            Produto produto = produtoService.buscarProdutoPorId(itemDto.getProdutoId());
            verificarProdutoDuplicado(produtosIds, itemDto.getProdutoId());

            PedidoItem item = new PedidoItem(pedido, produto, itemDto.getQuantidade(), produto.getPreco());
            pedido.addItem(item);
        }
    }


    @Transactional

    public void removerItemDoPedido(Long pedidoId, Long itemId) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado"));

        boolean removed = pedido.getItens().removeIf(item -> item.getId().equals(itemId));

        if (!removed) {
            throw new ResourceNotFoundException("Item não encontrado no pedido");
        }

        pedido.calcularEAtualizarValorTotal();
        pedidoRepository.save(pedido);
    }


    @Transactional
    public PedidoDTO atualizarEndereco(Long pedidoId, Long enderecoId) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado"));


        Endereco endereco = enderecoService.buscarEnderecoPorId(enderecoId);
        if (!endereco.getCliente().getId().equals(pedido.getCliente().getId())) {
            throw new BusinessException("Endereço não pertence ao cliente");
        }

        pedido.setEndereco(endereco);
        pedido = pedidoRepository.save(pedido);
        return toDTO(pedido);
    }


    private PedidoDTO toDTO(Pedido pedido) {

        PedidoDTO dto = new PedidoDTO();

        dto.setId(pedido.getId());
        dto.setDataCriacao(pedido.getDataCriacao());
        dto.setValorTotal(pedido.getValorTotal());
        dto.setClienteId(pedido.getCliente().getId());
        dto.setEnderecoId(pedido.getEndereco().getId());


        List<PedidoItemDTO> itensDTO = pedido.getItens().stream()
                .map(this::toItemDTO)
                .collect(Collectors.toList());
        dto.setItens(itensDTO);

        return dto;
    }


    private PedidoItemDTO toItemDTO(PedidoItem item) {

        PedidoItemDTO dto = new PedidoItemDTO();

        dto.setId(item.getId());
        dto.setQuantidade(item.getQuantidade());
        dto.setValorUnitario(item.getValorUnitario());
        dto.setProdutoId(item.getProduto().getId());

        return dto;
    }
}