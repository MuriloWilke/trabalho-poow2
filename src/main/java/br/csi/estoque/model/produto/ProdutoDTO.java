package br.csi.estoque.model.produto;

import br.csi.estoque.model.categoria.Categoria;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO que representa um produto sem o ID, para transferências de dados")
public class ProdutoDTO {

    @NonNull
    @NotNull
    @Size(max = 100, message = "O nome do produto não pode ter mais de 100 caracteres")
    @Schema(description = "Nome do produto", example = "Paracetamol")
    private String nome;

    @NonNull
    @NotNull
    @Size(max = 50, message = "A dosagem do produto não pode ter mais de 50 caracteres")
    @Schema(description = "Dosagem do produto", example = "500mg")
    private String dosagem;

    @NonNull
    @NotNull
    @Schema(description = "Quantidade do produto no estoque", example = "13")
    private int quantidade;

    @NonNull
    @NotNull
    @Schema(description = "Preço do produto", example = "19.99")
    private double preco;

    @NonNull
    @NotNull
    @Schema(description = "Indica se o produto necessita de uma receita", example = "false")
    private boolean receitaObrigatoria;

    @NonNull
    @NotNull
    @Schema(description = "Categorias associadas ao produto")
    private Set<Categoria> categorias = new HashSet<>();

    public ProdutoDTO(Produto produto) {
        this.nome = produto.getNome();
        this.dosagem = produto.getDosagem();
        this.quantidade = produto.getQuantidade();
        this.preco = produto.getPreco();
        this.receitaObrigatoria = produto.isReceitaObrigatoria();
        this.categorias = produto.getCategorias();
    }
}
