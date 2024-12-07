package br.csi.estoque.model.cliente;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Optional<Cliente> findByUuid(UUID uuid);
    void deleteByUuid(UUID uuid);
}
