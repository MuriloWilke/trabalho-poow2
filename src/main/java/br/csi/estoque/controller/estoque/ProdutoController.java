package br.csi.estoque.controller.estoque;

import br.csi.estoque.model.estoque.produto.Produto;
import br.csi.estoque.service.estoque.ProdutoService;
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
@RequestMapping("/produto")
@Tag(name = "Produtos", description = "Path relacionado a operações de produtos")
public class ProdutoController {

    private final ProdutoService service;

    public ProdutoController(ProdutoService service) {
        this.service = service;
    }

    @Operation(summary = "Listar todos os produtos", description = "Retorna uma lista de todos os produtos registrados.")
    @GetMapping("/listar")
    public List<Produto> listar() {
        return this.service.listar();
    }

    @Operation(summary = "Buscar produto por UUID", description = "Retorna um produto específico pelo seu UUID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto encontrado"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    @GetMapping("/{uuid}")
    public ResponseEntity<Produto> buscar(
            @Parameter(description = "UUID do produto a ser buscado", required = true)
            @PathVariable String uuid) {
        Optional<Produto> produto = this.service.getProdutoUUID(uuid);
        return produto.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Salvar novo produto", description = "Cadastra um novo produto.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Produto cadastrado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Produto.class))),
            @ApiResponse(responseCode = "400", description = "Erro na validação dos dados")
    })
    @PostMapping
    public ResponseEntity<Produto> salvar(@RequestBody @Valid Produto produto, UriComponentsBuilder uriBuilder) {
        this.service.salvar(produto);
        URI uri = uriBuilder.path("produto/{uuid}").buildAndExpand(produto.getUuid()).toUri();
        return ResponseEntity.created(uri).body(produto);
    }

    @Operation(summary = "Atualizar produto", description = "Atualiza as informações de um produto existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Produto atualizado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Produto.class))),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    @PutMapping
    public ResponseEntity<Produto> atualizarUUID(@RequestBody @Valid Produto produto) {
        this.service.atualizarUUID(produto);
        return ResponseEntity.ok(produto);
    }

    @Operation(summary = "Deletar produto", description = "Remove um produto pelo seu UUID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Produto deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })

    @Transactional
    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deletar(
            @Parameter(description = "UUID do produto a ser deletado", required = true)
            @PathVariable String uuid) {
        this.service.deletarUUID(uuid);
        return ResponseEntity.noContent().build();
    }
}
