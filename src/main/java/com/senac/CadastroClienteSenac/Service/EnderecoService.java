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
    private final EnderecoRepository enderecoRepository;
    private final ClienteRepository clienteRepository;

    @Autowired
    public EnderecoService(EnderecoRepository enderecoRepository, ClienteRepository clienteRepository) {
        this.enderecoRepository = enderecoRepository;
        this.clienteRepository = clienteRepository;
    }


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
        Optional.ofNullable(dto.getLogradouro()).ifPresent(endereco::setLogradouro);
        Optional.ofNullable(dto.getBairro()).ifPresent(endereco::setBairro);
        Optional.ofNullable(dto.getNumero()).ifPresent(endereco::setNumero);
        Optional.ofNullable(dto.getCidade()).ifPresent(endereco::setCidade);
        Optional.ofNullable(dto.getEstado()).ifPresent(endereco::setEstado);
        Optional.ofNullable(dto.getCep())
                .map(Integer::parseInt)
                .ifPresent(endereco::setCep);
    }

    private EnderecoDTO converterParaDTO(Endereco endereco) {
        return EnderecoDTO.builder()
                .id(endereco.getId())
                .logradouro(endereco.getLogradouro())
                .bairro(endereco.getBairro())
                .numero(endereco.getNumero())
                .cidade(endereco.getCidade())
                .estado(endereco.getEstado())
                .cep(String.valueOf(endereco.getCep()))
                .clienteId(endereco.getCliente().getId())
                .build();
    }

    private Endereco converterParaEntidade(EnderecoDTO dto, Cliente cliente) {
        return Endereco.builder()
                .logradouro(dto.getLogradouro())
                .bairro(dto.getBairro())
                .numero(dto.getNumero())
                .cidade(dto.getCidade())
                .estado(dto.getEstado())
                .cep(Integer.parseInt(dto.getCep()))
                .cliente(cliente)
                .build();
    }
}