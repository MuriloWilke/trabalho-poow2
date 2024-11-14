package br.csi.estoque.model.estoque.categoria;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    Optional<Categoria> findByUuid(UUID uuid);
    void deleteByUuid(UUID uuid);
}
