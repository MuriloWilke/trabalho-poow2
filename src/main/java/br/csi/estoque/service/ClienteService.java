package br.csi.estoque.service;

import br.csi.estoque.model.cliente.Cliente;
import br.csi.estoque.model.cliente.ClienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ClienteService {

    private final ClienteRepository repository;

    public ClienteService(ClienteRepository repository) {
        this.repository = repository;
    }

    public void salvar(Cliente cliente) {
        this.repository.save(cliente);
    }

    public List<Cliente> listar() {
        return this.repository.findAll();
    }

    public Optional<Cliente> getClienteUUID(String uuid) {
        UUID uuidFormatado = UUID.fromString(uuid);
        return this.repository.findByUuid(uuidFormatado);
    }

    public void atualizarUUID(Cliente cliente) {
        Optional<Cliente> optionalCliente = this.repository.findByUuid(cliente.getUuid());
        if(optionalCliente.isPresent()){
            Cliente c = optionalCliente.get();
            c.setIdade(cliente.getIdade());
            c.setEndereco(cliente.getEndereco());
            c.setUsuario(cliente.getUsuario());
            this.repository.save(c);
        }
        else {
            throw new RuntimeException("Cliente não encontrado");
        }
    }

    public void deletarUUID(String uuid) {
        this.repository.deleteByUuid(UUID.fromString(uuid));
    }
}
