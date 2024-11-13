package br.csi.estoque.model.usuarios.cliente;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Endereco {

    @NonNull
    @NotNull
    @Column(nullable = false, length = 9)
    @Size(max = 9, message = "O CEP do endereço do cliente não pode ter mais de 9 caracteres")
    @Schema(description = "CEP do endereço", example = "01001-000")
    private String cep;

    @NonNull
    @NotNull
    @Column(nullable = false, length = 2)
    @Size(max = 2, message = "A sigla do estado do endereço do cliente não pode ter mais de 2 caracteres")
    @Schema(description = "Estado do endereço", example = "SP")
    private String estado;

    @NonNull
    @NotNull
    @Column(nullable = false, length = 100)
    @Size(max = 100, message = "A cidade do endereço do cliente não pode ter mais de 100 caracteres")
    @Schema(description = "Cidade do endereço", example = "São Paulo")
    private String cidade;

    @NonNull
    @NotNull
    @Column(nullable = false, length = 100)
    @Size(max = 100, message = "O bairro do endereço do cliente não pode ter mais de 100 caracteres")
    @Schema(description = "Bairro do endereço", example = "Sé")
    private String bairro;

    @NonNull
    @NotNull
    @Column(nullable = false, length = 20)
    @Size(max = 20, message = "O número do endereço do cliente não pode ter mais de 20 caracteres")
    @Schema(description = "Número do endereço", example = "100")
    private String numero;

    @Column(nullable = true, length = 100)
    @Size(max = 100, message = "O complemento do endereço do cliente não pode ter mais de 9 caracteres")
    @Schema(description = "Complemento do endereço", example = "Apartamento 402")
    private String complemento;
}
