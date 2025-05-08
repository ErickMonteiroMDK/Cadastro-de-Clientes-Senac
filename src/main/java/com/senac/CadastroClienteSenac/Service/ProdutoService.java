package com.senac.CadastroClienteSenac.Service;

import com.senac.CadastroClienteSenac.Entity.Produto;
import com.senac.CadastroClienteSenac.Exeception.BusinessException;
import com.senac.CadastroClienteSenac.Exeception.ResourceNotFoundException;
import com.senac.CadastroClienteSenac.Repository.PedidoItemRepository;
import com.senac.CadastroClienteSenac.Repository.ProdutoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final PedidoItemRepository pedidoItemRepository;

    public ProdutoService(ProdutoRepository produtoRepository, PedidoItemRepository pedidoItemRepository) {
        this.produtoRepository = produtoRepository;
        this.pedidoItemRepository = pedidoItemRepository;
    }

    @Transactional
    public void excluirProduto(Long id) {
        if (pedidoItemRepository.existsByProdutoId(id)) {
            throw new BusinessException("Não é possível excluir produto pois está vinculado a pedidos");
        }

        produtoRepository.deleteById(id);
    }

    public Produto buscarProdutoPorId(Long id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado"));
    }
}