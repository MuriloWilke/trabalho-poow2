package br.csi.estoque.controller;

import br.csi.estoque.model.categoria.Categoria;
import br.csi.estoque.model.categoria.CategoriaDTO;
import br.csi.estoque.model.produto.Produto;
import br.csi.estoque.service.CategoriaService;
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
@RequestMapping("/categoria")
@Tag(name = "Categorias", description = "Path relacionado a operações de categorias")
public class CategoriaController {

    private final CategoriaService service;

    public CategoriaController(CategoriaService service) {
        this.service = service;
    }

    @Operation(summary = "Listar todas as categorias", description = "Retorna uma lista de todas as categorias registradas.")
    @GetMapping("/listar")
    public List<CategoriaDTO> listar() {
        List<Categoria> categorias = this.service.listar();
        return categorias.stream()
                .map(CategoriaDTO::new)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Buscar categoria por UUID", description = "Retorna uma categoria específica pelo seu UUID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoria encontrada"),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada")
    })
    @GetMapping("/{uuid}")
    public ResponseEntity<CategoriaDTO> buscar(
            @Parameter(description = "UUID da categoria a ser buscada", required = true)
            @PathVariable String uuid) {
        Optional<Categoria> categoria = this.service.getCategoriaUUID(uuid);
        return categoria.map(c -> ResponseEntity.ok(new CategoriaDTO(c)))
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Salvar nova categoria", description = "Cadastra uma nova categoria.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Categoria cadastrada com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CategoriaDTO.class))),
            @ApiResponse(responseCode = "400", description = "Erro na validação dos dados")
    })
    @PostMapping
    public ResponseEntity<CategoriaDTO> salvar(@RequestBody @Valid Categoria categoria, UriComponentsBuilder uriBuilder) {
        this.service.salvar(categoria);
        URI uri = uriBuilder.path("/categoria/{uuid}").buildAndExpand(categoria.getUuid()).toUri();
        return ResponseEntity.created(uri).body(new CategoriaDTO(categoria));
    }

    @Operation(summary = "Atualizar categoria", description = "Atualiza as informações de uma categoria existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Categoria atualizada com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CategoriaDTO.class))),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada")
    })
    @PutMapping
    public ResponseEntity<CategoriaDTO> atualizarUUID(@RequestBody @Valid Categoria categoria) {
        this.service.atualizarUUID(categoria);
        return ResponseEntity.ok(new CategoriaDTO(categoria));
    }

    @Operation(summary = "Adicionar Produto", description = "Adiciona um produto a uma categoria.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Produto adicionado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Produto ou categoria não encontrado/a")
    })
    @Transactional
    @PutMapping("/atribuir/{uuid}")
    public ResponseEntity<Void> atribuirCategoria(@PathVariable String uuid, @RequestBody Produto produto) {
        this.service.atribuirProduto(uuid, produto);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Deletar categoria", description = "Remove uma categoria pelo seu UUID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Categoria deletada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada")
    })
    @Transactional
    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deletar(
            @Parameter(description = "UUID da categoria a ser deletada", required = true)
            @PathVariable String uuid) {
        this.service.deletarUUID(uuid);
        return ResponseEntity.noContent().build();
    }
}
