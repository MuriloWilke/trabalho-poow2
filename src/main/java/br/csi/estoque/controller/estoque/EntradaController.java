package br.csi.estoque.controller.estoque;

import br.csi.estoque.model.estoque.entrada.Entrada;
import br.csi.estoque.service.estoque.EntradaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/entrada")
@Tag(name = "Entradas", description = "Path relacionado a operações de entradas")
public class EntradaController {

    private final EntradaService service;

    public EntradaController(EntradaService service) {
        this.service = service;
    }

    @Operation(summary = "Listar todas as entradas", description = "Retorna uma lista de todas as entradas registradas.")
    @GetMapping("/listar")
    public List<Entrada> listar() {
        return this.service.listar();
    }

    @Operation(summary = "Buscar entrada por UUID", description = "Retorna uma entrada específica pelo seu UUID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Entrada encontrada"),
            @ApiResponse(responseCode = "404", description = "Entrada não encontrada")
    })
    @GetMapping("/{uuid}")
    public ResponseEntity<Entrada> buscar(
            @Parameter(description = "UUID da entrada a ser buscada", required = true)
            @PathVariable String uuid) {
        Optional<Entrada> entrada = this.service.getEntradaUUID(uuid);
        return entrada.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Salvar nova entrada", description = "Cadastra uma nova entrada.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Entrada cadastrada com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Entrada.class))),
            @ApiResponse(responseCode = "400", description = "Erro na validação dos dados")
    })
    @PostMapping
    public ResponseEntity<Entrada> salvar(@RequestBody @Valid Entrada entrada, UriComponentsBuilder uriBuilder) {
        this.service.salvar(entrada);
        URI uri = uriBuilder.path("entrada/{uuid}").buildAndExpand(entrada.getUuid()).toUri();
        return ResponseEntity.created(uri).body(entrada);
    }

    @Operation(summary = "Atualizar entrada", description = "Atualiza as informações de uma entrada existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Entrada atualizada com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Entrada.class))),
            @ApiResponse(responseCode = "404", description = "Entrada não encontrada")
    })
    @PutMapping
    public ResponseEntity<Entrada> atualizarUUID(@RequestBody @Valid Entrada entrada) {
        this.service.atualizarUUID(entrada);
        return ResponseEntity.ok(entrada);
    }

    @Operation(summary = "Deletar entrada", description = "Remove uma entrada pelo seu UUID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Entrada deletada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Entrada não encontrada")
    })

    @Transactional
    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deletar(
            @Parameter(description = "UUID da entrada a ser deletada", required = true)
            @PathVariable String uuid) {
        this.service.deletarUUID(uuid);
        return ResponseEntity.noContent().build();
    }

}
