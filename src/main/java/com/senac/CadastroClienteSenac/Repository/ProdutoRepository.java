package com.senac.CadastroClienteSenac.Repository;

import com.senac.CadastroClienteSenac.Entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}