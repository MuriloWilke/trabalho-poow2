package br.csi.estoque.model.produto;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    Optional<Produto> findByUuid(UUID uuid);
    void deleteByUuid(UUID uuid);
}
