package br.csi.estoque.service.estoque;

import br.csi.estoque.model.estoque.produto.Produto;
import br.csi.estoque.model.estoque.produto.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProdutoService {

    private final ProdutoRepository repository;

    public ProdutoService(ProdutoRepository repository) {
        this.repository = repository;
    }

    public void salvar(Produto produto) {
        this.repository.save(produto);
    }

    public List<Produto> listar() {
        return this.repository.findAll();
    }

    public Optional<Produto> getProdutoUUID(String uuid) {
        UUID uuidFormatado = UUID.fromString(uuid);
        return this.repository.findByUuid(uuidFormatado);
    }

    public void atualizarUUID(Produto produto) {
        Optional<Produto> produtoOptional = this.repository.findByUuid(produto.getUuid());
        if (produtoOptional.isPresent()) {
            Produto p = produtoOptional.get();
            p.setDosagem(produto.getDosagem());
            p.setNome(produto.getNome());
            p.setPreco(produto.getPreco());
            p.setQuantidade(produto.getQuantidade());
            p.setCategoria(produto.getCategoria());
            p.setReceitaObrigatoria(produto.isReceitaObrigatoria());
            this.repository.save(p);
        }
        else{
            throw new RuntimeException("Produto não encontrado");
        }
    }

    public void deletarUUID(String uuid) {
        this.repository.deleteByUuid(UUID.fromString(uuid));
    }
}
