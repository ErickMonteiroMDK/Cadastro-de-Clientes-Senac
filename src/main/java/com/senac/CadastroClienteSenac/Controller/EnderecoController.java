package com.senac.CadastroClienteSenac.Controller;


import com.senac.CadastroClienteSenac.DTOs.EnderecoDTO.EnderecoDTO;
import com.senac.CadastroClienteSenac.Entity.Endereco;
import com.senac.CadastroClienteSenac.Service.EnderecoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/enderecos")
public class EnderecoController {

    @Autowired
    private EnderecoService enderecoService;

    @GetMapping
    public ResponseEntity<List<EnderecoDTO>> listarTodos() {
        List<EnderecoDTO> enderecos = enderecoService.listarTodos();
        return ResponseEntity.ok(enderecos);
    }

    @GetMapping("/cliente/{idCliente}")
    public ResponseEntity<List<EnderecoDTO>> listarPorCliente(@PathVariable Long idCliente) {
        List<EnderecoDTO> enderecos = enderecoService.listarPorCliente(idCliente);
        return ResponseEntity.ok(enderecos);
    }

    @PostMapping
    public ResponseEntity<EnderecoDTO> criar(@RequestBody EnderecoDTO enderecoDTO) {
        EnderecoDTO enderecoCriado = enderecoService.criar(enderecoDTO);
        return ResponseEntity.ok(enderecoCriado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EnderecoDTO> atualizar(
            @PathVariable Long id,
            @RequestBody EnderecoDTO enderecoDTO) {
        EnderecoDTO enderecoAtualizado = enderecoService.atualizar(id, enderecoDTO);
        return ResponseEntity.ok(enderecoAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        enderecoService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}

