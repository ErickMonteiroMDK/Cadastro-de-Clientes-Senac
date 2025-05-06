package com.senac.CadastroClienteSenac.Controller;

import com.senac.CadastroClienteSenac.Entity.Cliente;
import com.senac.CadastroClienteSenac.Service.ClienteService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
@AllArgsConstructor
public class ClienteController {

    private ClienteService service;

    @GetMapping("/listar")
    public List<Cliente> listarTodos() {
        return service.listarTodos();
    }

    @PostMapping("/criar")
    public Cliente criar(@Valid @RequestBody Cliente cliente) {
        return service.criar(cliente);
    }

    @PutMapping("/atualizar/{id}")
    public Cliente atualizar(@PathVariable Long id, @RequestBody Cliente cliente) {
        return service.atualizar(id, cliente);
    }

    @DeleteMapping("/excluir/{id}")
    public void excluir(@PathVariable Long id) {
        service.excluir(id);
    }

}