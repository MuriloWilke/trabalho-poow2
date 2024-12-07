package br.csi.estoque.model.entrada;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Schema(description = "DTO que representa a saída de um produto no estoque")
public class EntradaDTO {

    @Schema(description = "UUID da entrada", example = "550e8400-e29b-41d4-a716-446655440000")
    @NotNull
    private UUID uuid;

    @Schema(description = "ID do produto relacionado à entrada", example = "550e8400-e29b-41d4-a716-446655440000")
    @NotNull
    private UUID produtoUUID;

    @Schema(description = "Data e hora da entrada", example = "2024-12-06T12:00:00")
    @NotNull
    private LocalDateTime data;

    @Schema(description = "Quantidade do produto que foi entrou no estoque", example = "10")
    @NotNull
    private Integer quantidade;

    public EntradaDTO(Entrada entrada) {
        this.uuid = entrada.getUuid();
        this.produtoUUID = entrada.getProduto().getUuid();
        this.quantidade = entrada.getQuantidade();
        this.data = entrada.getData();
    }

}
