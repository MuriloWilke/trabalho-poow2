package br.csi.estoque.model.saida;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Schema(description = "DTO que representa a saída de um produto no estoque")
public class SaidaDTO {

    @Schema(description = "UUID da saída", example = "550e8400-e29b-41d4-a716-446655440000")
    @NotNull
    private UUID uuid;

    @Schema(description = "ID do cliente relacionado à saída", example = "550e8400-e29b-41d4-a716-446655440000")
    @NotNull
    private UUID clienteUUID;

    @Schema(description = "ID do usuário responsável pela saída", example = "550e8400-e29b-41d4-a716-446655440000")
    @NotNull
    private UUID usuarioUUID;

    @Schema(description = "ID do produto relacionado à saída", example = "550e8400-e29b-41d4-a716-446655440000")
    @NotNull
    private UUID produtoUUID;

    @Schema(description = "Data e hora da saída", example = "2024-12-06T12:00:00")
    @NotNull
    private LocalDateTime data;

    @Schema(description = "Quantidade do produto que foi retirada do estoque", example = "10")
    @NotNull
    private Integer quantidade;

    public SaidaDTO(Saida saida) {
        this.uuid = saida.getUuid();
        this.produtoUUID = saida.getProduto().getUuid();
        this.clienteUUID = saida.getCliente().getUuid();
        this.usuarioUUID = saida.getUsuario().getUuid();
        this.quantidade = saida.getQuantidade();
        this.data = saida.getData();
    }
}
