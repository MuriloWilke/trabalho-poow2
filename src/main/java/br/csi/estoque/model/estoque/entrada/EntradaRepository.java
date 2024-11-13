package br.csi.estoque.model.estoque.entrada;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface EntradaRepository extends JpaRepository<Entrada, Long> {
    Optional<Entrada> findEntradaByUuid(UUID uuid);
    void deleteEntradaByUuid(UUID uuid);
}
