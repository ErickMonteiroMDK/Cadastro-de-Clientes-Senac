package com.senac.CadastroClienteSenac.Controller;

import com.senac.CadastroClienteSenac.DTOs.ClienteDTO.ClienteAtualizarDTO;
import com.senac.CadastroClienteSenac.DTOs.ClienteDTO.ClienteCriarDTO;
import com.senac.CadastroClienteSenac.DTOs.ClienteDTO.ClienteRequestDTO;
import com.senac.CadastroClienteSenac.DTOs.ClienteDTO.ClienteResponseDTO;
import com.senac.CadastroClienteSenac.Entity.Cliente;
import com.senac.CadastroClienteSenac.Service.ClienteService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/clientes")
@AllArgsConstructor
public class ClienteController {

    private final ClienteService service;

    @GetMapping
    public ResponseEntity<List<ClienteResponseDTO>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @PostMapping
    public ResponseEntity<ClienteResponseDTO> criar(@Valid @RequestBody ClienteCriarDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.criar(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> atualizar(
            @PathVariable Long id, 
            @Valid @RequestBody ClienteAtualizarDTO dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @GetMapping("/filtrar")
    public ResponseEntity<List<ClienteResponseDTO>> filtrarClientes(
            @Valid ClienteRequestDTO filtro) {
        return ResponseEntity.ok(service.filtrarClientes(filtro));
    }
}