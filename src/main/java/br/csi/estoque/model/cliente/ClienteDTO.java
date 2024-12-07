package br.csi.estoque.model.cliente;

import br.csi.estoque.model.usuario.UsuarioDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Schema(description = "DTO que representa um cliente sem o ID")
public class ClienteDTO {

    @Schema(description = "UUID do cliente", example = "550e8400-e29b-41d4-a716-446655440000")
    private UUID uuid;

    @NonNull
    @NotNull
    @Schema(description = "Idade do cliente", example = "32")
    private int idade;

    @NonNull
    @NotNull
    @Schema(description = "Endereço do cliente")
    private Endereco endereco;

    @NonNull
    @NotNull
    @Schema(description = "Informações do usuário associado ao cliente")
    private UsuarioDTO usuario;

    public ClienteDTO(Cliente cliente) {
        this.uuid = cliente.getUuid();
        this.idade = cliente.getIdade();
        this.endereco = cliente.getEndereco();
        this.usuario = new UsuarioDTO(cliente.getUsuario());
    }
}
