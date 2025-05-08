package com.senac.CadastroClienteSenac.Repository;

import com.senac.CadastroClienteSenac.Entity.Cliente;
import com.senac.CadastroClienteSenac.Enum.Genero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ClienteRepository extends JpaRepository <Cliente, Long>{

    boolean existsByEmail(String email);

    @Query("SELECT c FROM Cliente c WHERE " +
            "(:nome IS NULL OR LOWER(c.nome) LIKE LOWER(CONCAT('%', :nome, '%'))) AND " +
            "(:documento IS NULL OR c.documento = :documento) AND " +
            "(:genero IS NULL OR c.genero = :genero) AND " +
            "(:dataNascimentoInicio IS NULL OR c.dataNascimento >= :dataNascimentoInicio) AND " +
            "(:dataNascimentoFim IS NULL OR c.dataNascimento <= :dataNascimentoFim)")
    List<Cliente> findByFilters(
            @Param("nome") String nome,
            @Param("documento") String documento,
            @Param("genero") Genero genero,
            @Param("dataNascimentoInicio") LocalDate dataNascimentoInicio,
            @Param("dataNascimentoFim") LocalDate dataNascimentoFim
    );

}