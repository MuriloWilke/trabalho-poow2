package br.csi.estoque.model.categoria;

import br.csi.estoque.model.produto.Produto;
import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "categorias")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Schema(description = "Entidade que representa uma categoria de produtos no sistema")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID de uma categoria", example = "1")
    private Long id;

    @UuidGenerator
    @Column(nullable = false, unique = true, updatable = false)
    @Schema(description = "UUID da categoria", example = "550e8400-e29b-41d4-a716-446655440000")
    private UUID uuid;

    @NonNull
    @NotNull
    @Column(nullable = false, length = 50)
    @Size(max = 50, message = "O nome da categoria não pode ter mais de 50 caracteres")
    @Schema(description = "Nome da categoria", example = "Medicamentos")
    private String nome;

    @ManyToMany(mappedBy = "categorias")
    @JsonBackReference
    @Schema(description = "Produtos associados a essa categoria")
    private Set<Produto> produtos = new HashSet<>();

}
