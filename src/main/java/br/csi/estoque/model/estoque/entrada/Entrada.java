package br.csi.estoque.model.estoque.entrada;

import br.csi.estoque.model.estoque.produto.Produto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "entradas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Schema(description = "Entidade que representa uma entrada no estoque do sistema")
public class Entrada {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID de uma entrada", example = "1")
    private Long id;

    @UuidGenerator
    @Column(nullable = false, unique = true, updatable = false)
    @Schema(description = "UUID da entrada", example = "550e8400-e29b-41d4-a716-446655440000")
    private UUID uuid;

    @NonNull
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_produto", nullable = false)
    @Schema(description = "Produto que recebeu uma entrada")
    private Produto produto;

    @NonNull
    @NotNull
    @Column(nullable = false)
    @Schema(description = "Quantidade da entrada de um produto", example = "5")
    private int quantidade;

    @NonNull
    @NotNull
    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    @Schema(description = "Data da entrada", example = "xx/xx/xxxx")
    private Date data;
}
