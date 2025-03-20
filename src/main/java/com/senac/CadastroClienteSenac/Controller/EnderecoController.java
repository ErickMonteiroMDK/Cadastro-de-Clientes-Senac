package com.senac.CadastroClienteSenac.Controller;


import com.senac.CadastroClienteSenac.Entity.Endereco;
import com.senac.CadastroClienteSenac.Service.EnderecoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/enderecos")
public class EnderecoController {

    @Autowired
    private EnderecoService enderecoService;

    @GetMapping("/listar")
    public List<Endereco> listarTodos() {
        return enderecoService.listarTodos();
    }

    @GetMapping("/listar/cliente/{idCliente}")
    public List<Endereco> listarPorCliente(@PathVariable Long idCliente) {
        return enderecoService.listarPorCliente(idCliente);
    }

    @PostMapping("/criar")
    public Endereco criar(@RequestBody Endereco endereco) {
        return enderecoService.criar(endereco);
    }

    @PutMapping("/atualizar/{id}")
    public Endereco atualizar(@PathVariable Long Id, @RequestBody Endereco endereco) {
        return enderecoService.atualizar(Id, endereco);
    }

    @DeleteMapping("/excluir/{id}")
    public void excluir(@PathVariable Long Id) {
        enderecoService.excluir(Id);
    }
}
