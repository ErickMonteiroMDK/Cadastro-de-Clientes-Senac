package com.senac.CadastroClienteSenac.Service;

import com.senac.CadastroClienteSenac.Entity.Cliente;
import com.senac.CadastroClienteSenac.Service.ClienteService;
import com.senac.CadastroClienteSenac.Repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repository;

    public List<Cliente> listarTodos() {
        return repository.findAll();
    }

    public Cliente criar(Cliente cliente) {
        if (cliente.getDocumento() == null || cliente.getNome() == null ||
                cliente.getSobrenome() == null || cliente.getEmail() == null) {
            throw new IllegalArgumentException("Os campos necessário não devem ser nulos!!!");

        }
        return repository.save(cliente);
    }

    public Cliente atualizar(Long id, Cliente cliente) {
        Optional<Cliente> existente = repository.findById(id);
        if (existente.isEmpty()){
            throw new IllegalArgumentException("O cliente não foi identificado!!!");
        }
        cliente.setId(id);
        return repository.save(cliente);
    }

    public void excluir(Long id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("O cliente não foi identificado!!!");
        }
        repository.deleteById(id);
    }
}