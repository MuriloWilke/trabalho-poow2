package br.csi.estoque.controller;

import br.csi.estoque.model.saida.Saida;
import br.csi.estoque.model.saida.SaidaDTO;
import br.csi.estoque.service.SaidaService;
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
import java.util.stream.Collectors;

@RestController
@RequestMapping("/saida")
@Tag(name = "Saidas", description = "Path relacionado a operações de saidas")
public class SaidaController {

    private final SaidaService service;

    public SaidaController(SaidaService service) {
        this.service = service;
    }

    @Operation(summary = "Listar todas as saídas", description = "Retorna uma lista de todas as saídas registradas.")
    @GetMapping("/listar")
    public List<SaidaDTO> listar() {
        List<Saida> saidas = this.service.listar();
        return saidas.stream()
                .map(SaidaDTO::new)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Buscar saída por UUID", description = "Retorna uma saída específica pelo seu UUID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Saída encontrada"),
            @ApiResponse(responseCode = "404", description = "Saída não encontrada")
    })
    @GetMapping("/{uuid}")
    public ResponseEntity<SaidaDTO> buscar(
            @Parameter(description = "UUID da saída a ser buscada", required = true)
            @PathVariable String uuid) {
        Optional<Saida> saida = this.service.getSaidaUUID(uuid);
        return saida.map(s -> ResponseEntity.ok(new SaidaDTO(s)))
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Salvar nova saída", description = "Cadastra uma nova saída.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Saída cadastrada com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SaidaDTO.class))),
            @ApiResponse(responseCode = "400", description = "Erro na validação dos dados")
    })
    @PostMapping
    public ResponseEntity<SaidaDTO> salvar(@RequestBody @Valid Saida saida, UriComponentsBuilder uriBuilder) {
        this.service.salvar(saida);
        URI uri = uriBuilder.path("/saida/{uuid}").buildAndExpand(saida.getUuid()).toUri();
        return ResponseEntity.created(uri).body(new SaidaDTO(saida));
    }

    @Operation(summary = "Atualizar saída", description = "Atualiza as informações de uma saída existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Saída atualizada com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SaidaDTO.class))),
            @ApiResponse(responseCode = "404", description = "Saída não encontrada")
    })
    @PutMapping
    public ResponseEntity<SaidaDTO> atualizarUUID(@RequestBody @Valid Saida saida) {
        this.service.atualizarUUID(saida);
        return ResponseEntity.ok(new SaidaDTO(saida));
    }

    @Operation(summary = "Deletar saída", description = "Remove uma saída pelo seu UUID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Saída deletada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Saída não encontrada")
    })
    @Transactional
    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deletar(
            @Parameter(description = "UUID da saída a ser deletada", required = true)
            @PathVariable String uuid) {
        this.service.deletarUUID(uuid);
        return ResponseEntity.noContent().build();
    }
}
