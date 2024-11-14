package br.csi.estoque.controller.estoque;

import br.csi.estoque.model.estoque.categoria.Categoria;
import br.csi.estoque.model.estoque.produto.Produto;
import br.csi.estoque.service.estoque.CategoriaService;
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
@RequestMapping("/categoria")
@Tag(name = "Categorias", description = "Path relacionado a operações de categorias")
public class CategoriaController {

    private final CategoriaService service;

    public CategoriaController(CategoriaService service) {
        this.service = service;
    }

    @Operation(summary = "Listar todos os categorias", description = "Retorna uma lista de todos os categorias registrados.")
    @GetMapping("/listar")
    public List<Categoria> listar() {
        return this.service.listar();
    }

    @Operation(summary = "Buscar categoria por UUID", description = "Retorna um categoria específico pelo seu UUID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoria encontrado"),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrado")
    })
    @GetMapping("/{uuid}")
    public ResponseEntity<Categoria> buscar(
            @Parameter(description = "UUID do categoria a ser buscado", required = true)
            @PathVariable String uuid) {
        Optional<Categoria> categoria = this.service.getCategoriaUUID(uuid);
        return categoria.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Salvar novo categoria", description = "Cadastra um novo categoria.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Categoria cadastrado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Categoria.class))),
            @ApiResponse(responseCode = "400", description = "Erro na validação dos dados")
    })
    @PostMapping
    public ResponseEntity<Categoria> salvar(@RequestBody @Valid Categoria categoria, UriComponentsBuilder uriBuilder) {
        this.service.salvar(categoria);
        URI uri = uriBuilder.path("/categoria/{uuid}").buildAndExpand(categoria.getUuid()).toUri();
        return ResponseEntity.created(uri).body(categoria);
    }

    @Operation(summary = "Atualizar categoria", description = "Atualiza as informações de um categoria existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Categoria atualizado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Categoria.class))),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrado")
    })
    @PutMapping
    public ResponseEntity<Categoria> atualizarUUID(@RequestBody @Valid Categoria categoria) {
        this.service.atualizarUUID(categoria);
        return ResponseEntity.ok(categoria);
    }

    @Operation(summary = "Adicionar Produto", description = "Adiciona um produto a uma categoria.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Produto adicionado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Produto ou categoria não encontrado/a")
    })
    @Transactional
    @PutMapping("/atribuir/{uuid}")
    public ResponseEntity atribuirCategoria(@PathVariable String uuid, @RequestBody Produto produto) {
        return ResponseEntity.ok(this.service.atribuirProduto(uuid, produto));
    }

    @Operation(summary = "Deletar categoria", description = "Remove um categoria pelo seu UUID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Categoria deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrado")
    })
    @Transactional
    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deletar(
            @Parameter(description = "UUID do categoria a ser deletado", required = true)
            @PathVariable String uuid) {
        this.service.deletarUUID(uuid);
        return ResponseEntity.noContent().build();
    }
    
}
