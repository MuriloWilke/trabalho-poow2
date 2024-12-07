package br.csi.estoque.service;

import br.csi.estoque.model.saida.Saida;
import br.csi.estoque.model.saida.SaidaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SaidaService {

    private final SaidaRepository repository;

    public SaidaService(SaidaRepository repository) {
        this.repository = repository;
    }

    public void salvar(Saida saida) {
        this.repository.save(saida);
    }

    public List<Saida> listar() {
        return this.repository.findAll();
    }

    public Optional<Saida> getSaidaUUID(String uuid) {
        UUID uuidFormatado = UUID.fromString(uuid);
        return this.repository.findByUuid(uuidFormatado);
    }

    public void atualizarUUID(Saida saida) {
        Optional<Saida> optionalSaida = this.repository.findByUuid(saida.getUuid());
        if(optionalSaida.isPresent()) {
            Saida s = optionalSaida.get();
            s.setCliente(saida.getCliente());
            s.setUsuario(saida.getUsuario());
            s.setProduto(saida.getProduto());
            s.setQuantidade(saida.getQuantidade());
            this.repository.save(s);
        }
        else{
            throw new RuntimeException("Saída não encontrada");
        }
    }

    public void deletarUUID(String uuid) {
        this.repository.deleteByUuid(UUID.fromString(uuid));
    }
}
