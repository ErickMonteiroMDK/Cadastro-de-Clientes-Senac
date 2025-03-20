package com.senac.CadastroClienteSenac.Service;

import com.senac.CadastroClienteSenac.Entity.Endereco;
import com.senac.CadastroClienteSenac.Repository.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class EnderecoService {

    @Autowired
    private EnderecoRepository enderecoRepository;

    public List<Endereco> listarTodos() {
        return enderecoRepository.findAll();
    }

    public List<Endereco> listarPorCliente(Long idCliente) {
        return enderecoRepository.findByClienteId(idCliente);
    }

    public Endereco criar(Endereco endereco) {
        List<Endereco> enderecosCliente = listarPorCliente(endereco.getCliente().getId());

        if (enderecosCliente.size() >= 3) {
            throw new RuntimeException("O cliente já possui 3 endereços. Não é possível cadastrar mais.");
        }

        return enderecoRepository.save(endereco);
    }

    public Endereco atualizar(Long id, Endereco enderecoAtualizado) {
        Optional<Endereco> enderecoExistente = enderecoRepository.findById(id);

        if (enderecoExistente.isEmpty()) {
            throw new RuntimeException("Endereço não encontrado!!!.");
        }
        Endereco endereco = enderecoExistente.get();

        if (!endereco.getCliente().getId().equals(enderecoAtualizado.getCliente().getId())) {
            throw new RuntimeException("Não é permitido trocar o cliente do endereço!!!.");
        }


        List<Endereco> enderecosCliente = listarPorCliente(endereco.getCliente().getId());
        if (enderecosCliente.size() >= 3) {
            throw new RuntimeException("O cliente já possui 3 endereços. Não é possível atualizar o endereço!!!.");
        }
        endereco.setLogradouro(enderecoAtualizado.getLogradouro());
        endereco.setBairro(enderecoAtualizado.getBairro());
        endereco.setNumero(enderecoAtualizado.getNumero());
        endereco.setCidade(enderecoAtualizado.getCidade());
        endereco.setEstado(enderecoAtualizado.getEstado());
        endereco.setCep(enderecoAtualizado.getCep());

        return enderecoRepository.save(endereco);
    }
    public void excluir(Long id) {
        if (!enderecoRepository.existsById(id)) {
            throw new RuntimeException("Endereço não encontrado. Não é possível excluir!!!.");
        }

        enderecoRepository.deleteById(id);
    }
}
