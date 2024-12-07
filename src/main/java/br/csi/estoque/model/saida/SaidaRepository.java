package br.csi.estoque.model.saida;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SaidaRepository extends JpaRepository<Saida, Long> {
    Optional<Saida> findByUuid(UUID uuid);
    void deleteByUuid(UUID uuid);
}
