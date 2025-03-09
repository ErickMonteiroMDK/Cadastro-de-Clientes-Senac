package com.senac.CadastroClienteSenac.Repository;

import com.senac.CadastroClienteSenac.Entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository <Cliente, Long>{
}