package com.senac.CadastroClienteSenac.Repository;

import com.senac.CadastroClienteSenac.Entity.PedidoItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoItemRepository extends JpaRepository<PedidoItem, Long> {
    boolean existsByProdutoId(Long produtoId);
}
