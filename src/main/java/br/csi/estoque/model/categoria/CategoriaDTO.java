package br.csi.estoque.model.categoria;

import br.csi.estoque.model.produto.Produto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Schema(description = "DTO que representa uma categoria sem o ID")
public class CategoriaDTO {

    @Schema(description = "UUID da categoria", example = "550e8400-e29b-41d4-a716-446655440000")
    private UUID uuid;

    @NonNull
    @NotNull
    @Schema(description = "Nome da categoria", example = "Medicamentos")
    private String nome;

    @Schema(description = "Produtos associados a essa categoria")
    private Set<Produto> produtos;

    public CategoriaDTO(Categoria categoria) {
        this.uuid = categoria.getUuid();
        this.nome = categoria.getNome();
        this.produtos = categoria.getProdutos();
    }
}

