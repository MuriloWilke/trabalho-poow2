package br.csi.estoque.service.estoque;

import br.csi.estoque.model.estoque.entrada.Entrada;
import br.csi.estoque.model.estoque.entrada.EntradaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EntradaService {

    private final EntradaRepository repository;

    public EntradaService(EntradaRepository repository) {
        this.repository = repository;
    }

    public void salvar(Entrada entrada) {
        this.repository.save(entrada);
    }

    public List<Entrada> listar() {
        return this.repository.findAll();
    }

    public Optional<Entrada> getEntradaUUID(String uuid) {
        UUID uuidFormatado = UUID.fromString(uuid);
        return this.repository.findByUuid(uuidFormatado);
    }

    public void atualizarUUID(Entrada entrada) {
        Optional<Entrada> entradaOptional = this.repository.findByUuid(entrada.getUuid());
        if (entradaOptional.isPresent()) {
            Entrada e = entradaOptional.get();
            e.setProduto(entrada.getProduto());
            e.setQuantidade(entrada.getQuantidade());
            this.repository.save(e);
        }
        else{
            throw new RuntimeException("Entrada não encontrada");
        }
    }

    public void deletarUUID(String uuid) {
        this.repository.deleteByUuid(UUID.fromString(uuid));
    }
}
