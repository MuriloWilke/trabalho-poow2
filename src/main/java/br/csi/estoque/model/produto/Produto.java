package br.csi.estoque.model.produto;

import br.csi.estoque.model.categoria.Categoria;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
@Table(name = "produtos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Schema(description = "Entidade que representa um produto no sistema")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID de um produto", example = "1")
    private Long id;

    @UuidGenerator
    @Column(nullable = false, unique = true, updatable = false)
    @Schema(description = "UUID do produto", example = "550e8400-e29b-41d4-a716-446655440000")
    private UUID uuid;

    @NonNull
    @NotNull
    @Column(nullable = false, length = 100)
    @Size(max = 100, message = "O nome do produto não pode ter mais de 100 caracteres")
    @Schema(description = "Nome do produto", example = "Paracetamol")
    private String nome;

    @NonNull
    @NotNull
    @Column(nullable = false, length = 50)
    @Size(max = 50, message = "A dosagem do produto não pode ter mais de 50 caracteres")
    @Schema(description = "Dosagem do produto", example = "500mg")
    private String dosagem;

    @NonNull
    @NotNull
    @Column(nullable = false)
    @Schema(description = "Quantidade do produto no estoque", example = "13")
    private int quantidade;

    @NonNull
    @NotNull
    @Column(nullable = false)
    @Schema(description = "Preço do produto", example = "19.99")
    private double preco;

    @NonNull
    @NotNull
    @Column(nullable = false)
    @Schema(description = "Indica se o produto necessita de uma receita", example = "false")
    private boolean receitaObrigatoria;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "produto_categoria",
            joinColumns = @JoinColumn(name = "id_produto"),
            inverseJoinColumns = @JoinColumn(name = "id_categoria")
    )
    @JsonManagedReference
    @Schema(description = "Categorias associadas ao produto")
    private Set<Categoria> categorias = new HashSet<>();
}
