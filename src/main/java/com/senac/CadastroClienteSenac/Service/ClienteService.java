package com.senac.CadastroClienteSenac.Service;

import com.senac.CadastroClienteSenac.DTOs.ClienteDTO.*;
import com.senac.CadastroClienteSenac.DTOs.EnderecoDTO.EnderecoDTO;
import com.senac.CadastroClienteSenac.Entity.Cliente;
import com.senac.CadastroClienteSenac.Exeception.ClienteNotFoundException;
import com.senac.CadastroClienteSenac.Repository.ClienteRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ClienteService {

    private ClienteRepository repository;

    public List<ClienteResponseDTO> listarTodos() {
        return repository.findAll().stream()
                .map(this::converterParaResponseDTO)
                .collect(Collectors.toList());
    }

    public ClienteResponseDTO buscarPorId(Long id) {
        return repository.findById(id)
                .map(this::converterParaResponseDTO)
                .orElseThrow(() -> new ClienteNotFoundException(id));
    }

    public Cliente buscarEntidadePorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ClienteNotFoundException(id));
    }

    @Transactional
    public ClienteResponseDTO criar(ClienteCriarDTO dto) {
        if (repository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("Já existe um cliente com este email");
        }

        Cliente cliente = Cliente.builder()
                .documento(dto.getDocumento())
                .nome(dto.getNome())
                .sobrenome(dto.getSobrenome())
                .email(dto.getEmail())
                .ddd(dto.getDdd())
                .telefone(dto.getTelefone())
                .genero(dto.getGenero())
                .dataNascimento(dto.getDataNascimento())
                .build();

        Cliente clienteSalvo = repository.save(cliente);
        return converterParaResponseDTO(clienteSalvo);
    }

    @Transactional
    public ClienteResponseDTO atualizar(Long id, ClienteAtualizarDTO dto) {
        return repository.findById(id)
                .map(cliente -> {
                    if (dto.getDocumento() != null) cliente.setDocumento(dto.getDocumento());
                    if (dto.getNome() != null) cliente.setNome(dto.getNome());
                    if (dto.getSobrenome() != null) cliente.setSobrenome(dto.getSobrenome());
                    if (dto.getEmail() != null) cliente.setEmail(dto.getEmail());
                    if (dto.getDdd() != null) cliente.setDdd(dto.getDdd());
                    if (dto.getTelefone() != null) cliente.setTelefone(dto.getTelefone());
                    if (dto.getGenero() != null) cliente.setGenero(dto.getGenero());
                    if (dto.getDataNascimento() != null) cliente.setDataNascimento(dto.getDataNascimento());

                    Cliente clienteAtualizado = repository.save(cliente);
                    return converterParaResponseDTO(clienteAtualizado);
                })
                .orElseThrow(() -> new ClienteNotFoundException(id));
    }

    public List<ClienteResponseDTO> filtrarClientes(ClienteRequestDTO filtro) {
        List<Cliente> clientes = repository.findByFilters(
                filtro.getNome(),
                filtro.getDocumento(),
                filtro.getGenero(),
                filtro.getDataNascimentoInicio(),
                filtro.getDataNascimentoFim()
        );

        return clientes.stream()
                .map(this::converterParaResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public void excluir(Long id) {
        if (!repository.existsById(id)) {
            throw new ClienteNotFoundException(id);
        }
        repository.deleteById(id);
    }

    private ClienteResponseDTO converterParaResponseDTO(Cliente cliente) {
        return ClienteResponseDTO.builder()
                .id(cliente.getId())
                .documento(cliente.getDocumento())
                .nome(cliente.getNome())
                .sobrenome(cliente.getSobrenome())
                .email(cliente.getEmail())
                .ddd(cliente.getDdd())
                .telefone(cliente.getTelefone())
                .genero(cliente.getGenero())
                .dataNascimento(cliente.getDataNascimento())
                .idade(calcularIdade(cliente.getDataNascimento()))
                .enderecos(cliente.getEnderecos() != null ?
                        cliente.getEnderecos().stream()
                                .map(endereco -> EnderecoDTO.builder()
                                        .id(endereco.getId())
                                        .logradouro(endereco.getLogradouro())
                                        .bairro(endereco.getBairro())
                                        .numero(endereco.getNumero())
                                        .cidade(endereco.getCidade())
                                        .estado(endereco.getEstado())
                                        .cep(String.valueOf(endereco.getCep()))
                                        .clienteId(endereco.getCliente().getId())
                                        .build())
                                .collect(Collectors.toList()) :
                        null)
                .build();
    }

    private int calcularIdade(LocalDate dataNascimento) {
        return Period.between(dataNascimento, LocalDate.now()).getYears();
    }
}