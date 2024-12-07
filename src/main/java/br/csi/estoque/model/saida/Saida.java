package br.csi.estoque.model.saida;

import br.csi.estoque.model.produto.Produto;
import br.csi.estoque.model.cliente.Cliente;
import br.csi.estoque.model.usuario.Usuario;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "saidas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Schema(description = "Entidade que representa uma saída no estoque do sistema")
public class Saida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID da saída", example = "1")
    private Long id;

    @UuidGenerator
    @Column(nullable = false, unique = true, updatable = false)
    @Schema(description = "UUID da saída", example = "550e8400-e29b-41d4-a716-446655440000")
    private UUID uuid;

    @NonNull
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cliente", nullable = false)
    @Schema(description = "Cliente da saída")
    private Cliente cliente;

    @NonNull
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    @Schema(description = "Atendente da saída")
    private Usuario usuario;

    @NonNull
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_produto", nullable = false)
    @Schema(description = "Produto da saída")
    private Produto produto;

    @NonNull
    @Column(nullable = false)
    @CreationTimestamp
    @Schema(description = "Data da saída", example = "2024-12-06T12:00:00")
    private LocalDateTime data;

    @NonNull
    @NotNull
    @Column(nullable = false)
    @Schema(description = "Quantidade que foi vendida do produto", example = "5")
    private int quantidade;
}
