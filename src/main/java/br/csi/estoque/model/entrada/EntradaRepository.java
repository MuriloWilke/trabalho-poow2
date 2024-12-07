package br.csi.estoque.model.entrada;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface EntradaRepository extends JpaRepository<Entrada, Long> {
    Optional<Entrada> findByUuid(UUID uuid);
    void deleteByUuid(UUID uuid);
}
