package com.senac.CadastroClienteSenac.Service;

import com.senac.CadastroClienteSenac.DTOs.ClienteDTO.*;
import com.senac.CadastroClienteSenac.DTOs.EnderecoDTO.EnderecoResponseDTO;
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

    //Listar todos os clientes
    public List<ClienteResponseDTO> listarTodos() {
        return repository.findAll().stream()
                .map(this::converterParaResponseDTO)
                .collect(Collectors.toList());
    }

    //Buscar pelo ID
    public ClienteResponseDTO buscarPorId(Long id) {
        return repository.findById(id)
                .map(this::converterParaResponseDTO)
                .orElseThrow(() -> new ClienteNotFoundException(id));
    }

    //Criar Cliente
    @Transactional
    public ClienteResponseDTO criar(ClienteCriarDTO dto) {
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

    //Atualizar Cliente
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

    //Filtro de busca
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

    //Excluir Cliente
    @Transactional
    public void excluir(Long id) {
        if (!repository.existsById(id)) {
            throw new ClienteNotFoundException(id);
        }
        repository.deleteById(id);
    }

    //Separação da camada de domínio da camada de apresentação
    //1. **Conversão de Dados**: Transformar uma entidade em um DTO () `Cliente``ClienteResponseDTO`
    //2. **Camada de Apresentação**: Preparar os dados que serão enviados para o cliente/frontend de forma adequada
    //3. **Segurança**: Controlar quais informações serão expostas na API, evitando enviar dados sensíveis ou desnecessários
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
                        .map(endereco -> EnderecoResponseDTO.builder()
                            // Preencher campos do EnderecoResponseDTO
                            .build())
                        .collect(Collectors.toList()) : 
                    null)
                .build();
    }

    private int calcularIdade(LocalDate dataNascimento) {
        return Period.between(dataNascimento, LocalDate.now()).getYears();
    }
}