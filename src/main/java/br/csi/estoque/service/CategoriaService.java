package br.csi.estoque.service;

import br.csi.estoque.model.categoria.CategoriaRepository;
import br.csi.estoque.model.categoria.Categoria;
import br.csi.estoque.model.produto.Produto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CategoriaService {

    private final CategoriaRepository repository;

    public CategoriaService(CategoriaRepository repository) {
        this.repository = repository;
    }

    public void salvar(Categoria categoria) {
        this.repository.save(categoria);
    }

    public List<Categoria> listar() {
        return this.repository.findAll();
    }

    public Optional<Categoria> getCategoriaUUID(String uuid) {
        UUID uuidFormatado = UUID.fromString(uuid);
        return this.repository.findByUuid(uuidFormatado);
    }

    public void atualizarUUID(Categoria categoria) {
        Optional<Categoria> categoriaOptional = this.repository.findByUuid(categoria.getUuid());
        if (categoriaOptional.isPresent()) {
            Categoria c = categoriaOptional.get();
            c.setNome(categoria.getNome());
            this.repository.save(c);
        }
        else{
            throw new RuntimeException("Categoria não encontrada");
        }
    }

    public String atribuirProduto(String uuid, Produto produto) {

        UUID uuidFormatado = UUID.fromString(uuid);

        Optional<Categoria> categoriaOptional = this.repository.findByUuid(uuidFormatado);
        if (categoriaOptional.isPresent()) {
            Categoria categoria = categoriaOptional.get();

            categoria.getProdutos().add(produto);
            produto.getCategorias().add(categoria);

            this.repository.save(categoria);

            return "Projeto atribuído com sucesso";

        } else {
            return "Projeto ou categoria não encontrado/a";
        }

    }

    public void deletarUUID(String uuid) {
        this.repository.deleteByUuid(UUID.fromString(uuid));
    }
    
}
