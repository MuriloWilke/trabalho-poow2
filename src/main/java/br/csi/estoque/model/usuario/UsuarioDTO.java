package br.csi.estoque.model.usuario;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO que representa um usuário sem o ID, para transferências de dados")
public class UsuarioDTO {

    @Schema(description = "UUID do usuário", example = "550e8400-e29b-41d4-a716-446655440000")
    @NotNull
    private UUID uuid;

    @NonNull
    @NotNull
    @Size(max = 50, message = "O nome do usuário não pode ter mais de 50 caracteres")
    @Schema(description = "Nome do usuário", example = "Marcos")
    private String nome;

    @NonNull
    @NotNull
    @Email(message = "O email fornecido está incorreto")
    @Size(max = 100, message = "O tamanho do email não pode ser maior que 100 caracteres")
    @Schema(description = "Email do usuário", example = "exemplo@exemplo.com")
    private String email;

    @NonNull
    @NotNull
    @Size(max = 255, message = "O tamanho da senha não pode ter mais de 255 caracteres")
    @Schema(description = "Senha do usuário", example = "1234")
    private String senha;

    @NonNull
    @NotNull
    @Schema(description = "Data do cadastro do usuário", example = "2024-12-06T12:00:00")
    private LocalDateTime data_cadastro;

    @NonNull
    @NotNull
    @Size(max = 20, message = "O tamanho da permissão do usuário não pode ser maior que 20 caracteres")
    @Schema(description = "Permissão do usuário", example = "ROLE_ADMIN")
    private String permissao;

    public UsuarioDTO(Usuario usuario) {
        this.uuid = usuario.getUuid();
        this.nome = usuario.getNome();
        this.email = usuario.getEmail();
        this.senha = usuario.getSenha();
        this.data_cadastro = usuario.getData_cadastro();
        this.permissao = usuario.getPermissao();
    }
}

