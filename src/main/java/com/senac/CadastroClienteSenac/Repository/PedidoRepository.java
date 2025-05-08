package com.senac.CadastroClienteSenac.Repository;

import com.senac.CadastroClienteSenac.Entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
}
