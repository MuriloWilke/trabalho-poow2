package br.csi.estoque.model.cliente;

import br.csi.estoque.model.usuario.Usuario;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;
import java.util.UUID;

@Entity
@Table(name = "clientes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Schema(description = "Entidade que representa um cliente no sistema")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID do cliente", example = "1")
    private Long id;

    @UuidGenerator
    @Column(nullable = false, unique = true, updatable = false)
    @Schema(description = "UUID do cliente", example = "550e8400-e29b-41d4-a716-446655440000")
    private UUID uuid;

    @NonNull
    @NotNull
    @Column(nullable = false)
    @Schema(description = "Idade do cliente", example = "32")
    private int idade;

    @NonNull
    @NotNull
    @Embedded
    @Schema(description = "Endereço do cliente")
    private Endereco endereco;

    @NonNull
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_usuario", nullable = false)
    @Schema(description = "ID do usuario do cliente")
    private Usuario usuario;
}
