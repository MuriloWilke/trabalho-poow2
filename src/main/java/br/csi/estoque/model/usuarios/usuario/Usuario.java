package br.csi.estoque.model.usuarios.usuario;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.UuidGenerator;

import java.sql.Date;
import java.util.UUID;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@Schema(description = "Entidade que representa um usuario no sistema")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID do usuario", example = "1")
    private Long id;

    @UuidGenerator
    @Column(nullable = false, updatable = false, unique = true)
    @Schema(description = "UUID do usuario", example = "550e8400-e29b-41d4-a716-446655440000")
    private UUID uuid;

    @NonNull
    @NotNull
    @Column(nullable = false, length = 50)
    @Size(max = 50, message = "O nome do usuário não pode ter mais de 50 caracteres")
    @Schema(description = "Nome do usuario", example = "Marcos")
    private String nome;

    @NonNull
    @NotNull
    @Column(nullable = false, length = 100, unique = true)
    @Email(message = "O email fornecido está incorreto")
    @Schema(description = "Email do usuario", example = "exemplo@exemplo.com")
    private String email;

    @NonNull
    @NotNull
    @Column(nullable = false, length = 255)
    @Size(max = 255, message = "O tamanho da senha do usuário não pode ter mais de 255 caracteres")
    @Schema(description = "Senha do usuario", example = "1234")
    private String senha;

    @NonNull
    @NotNull
    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    @Schema(description = "Data do cadastro do usuario", example = "xx/xx/xxxx")
    private Date data_cadastro;

    @NonNull
    @NotNull
    @Column(nullable = false)
    @Size(max = 20, message = "O tamanho da permissão do usuário não pode ter mais de 20 caracteres")
    @Schema(description = "Permissão do usuario", example = "ADMIN")
    private String permissao;

}