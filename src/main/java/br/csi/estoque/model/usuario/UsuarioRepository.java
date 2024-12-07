package br.csi.estoque.model.usuario;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByUuid(UUID uuid);

    Usuario findByEmail(String email);
    void deleteByUuid(UUID uuid);
}
