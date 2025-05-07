package com.senac.CadastroClienteSenac.Service;

import com.senac.CadastroClienteSenac.DTOs.EnderecoDTO.EnderecoDTO;
import com.senac.CadastroClienteSenac.Entity.Cliente;
import com.senac.CadastroClienteSenac.Entity.Endereco;
import com.senac.CadastroClienteSenac.Exeception.EnderecoException;
import com.senac.CadastroClienteSenac.Repository.ClienteRepository;
import com.senac.CadastroClienteSenac.Repository.EnderecoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EnderecoService {


    private static final int LIMITE_ENDERECOS = 3;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    public List<EnderecoDTO> listarTodos() {
        return enderecoRepository.findAll()
                .stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }

    public List<EnderecoDTO> listarPorCliente(Long idCliente) {
        validarExistenciaCliente(idCliente);
        return enderecoRepository.findByClienteId(idCliente)
                .stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public EnderecoDTO criar(EnderecoDTO enderecoDTO) {
        validarLimiteEnderecos(enderecoDTO.getClienteId());

        Cliente cliente = buscarCliente(enderecoDTO.getClienteId());
        Endereco endereco = converterParaEntidade(enderecoDTO, cliente);

        return converterParaDTO(enderecoRepository.save(endereco));
    }

    @Transactional
    public EnderecoDTO atualizar(Long id, EnderecoDTO enderecoDTO) {
        Endereco enderecoExistente = buscarEnderecoPorId(id);

        validarClienteEndereco(enderecoExistente.getCliente().getId(), enderecoDTO.getClienteId());

        atualizarDadosEndereco(enderecoExistente, enderecoDTO);

        return converterParaDTO(enderecoRepository.save(enderecoExistente));
    }

    @Transactional
    public void excluir(Long id) {
        if (!enderecoRepository.existsById(id)) {
            throw new EnderecoException("Endereço não encontrado. Não é possível excluir.");
        }
        enderecoRepository.deleteById(id);
    }

    private void validarExistenciaCliente(Long clienteId) {
        if (!clienteRepository.existsById(clienteId)) {
            throw new EnderecoException("Cliente não encontrado.");
        }
    }

    private void validarLimiteEnderecos(Long clienteId) {
        if (enderecoRepository.findByClienteId(clienteId).size() >= LIMITE_ENDERECOS) {
            throw new EnderecoException("O cliente já possui " + LIMITE_ENDERECOS + " endereços. Não é possível cadastrar mais.");
        }
    }

    private Cliente buscarCliente(Long clienteId) {
        return clienteRepository.findById(clienteId)
                .orElseThrow(() -> new EnderecoException("Cliente não encontrado."));
    }

    private Endereco buscarEnderecoPorId(Long id) {
        return enderecoRepository.findById(id)
                .orElseThrow(() -> new EnderecoException("Endereço não encontrado."));
    }

    private void validarClienteEndereco(Long clienteAtual, Long clienteNovo) {
        if (!clienteAtual.equals(clienteNovo)) {
            throw new EnderecoException("Não é permitido trocar o cliente do endereço.");
        }
    }

    private void atualizarDadosEndereco(Endereco endereco, EnderecoDTO dto) {
        endereco.setLogradouro(dto.getLogradouro());
        endereco.setBairro(dto.getBairro());
        endereco.setNumero(dto.getNumero());
        endereco.setCidade(dto.getCidade());
        endereco.setEstado(dto.getEstado());
        endereco.setCep(Integer.parseInt(dto.getCep()));
    }

    private EnderecoDTO converterParaDTO(Endereco endereco) {
        EnderecoDTO dto = new EnderecoDTO();
        dto.setId(endereco.getId());
        dto.setLogradouro(endereco.getLogradouro());
        dto.setBairro(endereco.getBairro());
        dto.setNumero(endereco.getNumero());
        dto.setCidade(endereco.getCidade());
        dto.setEstado(endereco.getEstado());
        dto.setCep(endereco.getCep());
        dto.setClienteId(endereco.getCliente().getId());
        return dto;
    }

    private Endereco converterParaEntidade(EnderecoDTO dto, Cliente cliente) {
        Endereco endereco = new Endereco();
        endereco.setLogradouro(dto.getLogradouro());
        endereco.setBairro(dto.getBairro());
        endereco.setNumero(dto.getNumero());
        endereco.setCidade(dto.getCidade());
        endereco.setEstado(dto.getEstado());
        endereco.setCep(Integer.parseInt(dto.getCep()));
        endereco.setCliente(cliente);
        return endereco;
    }
}
