package br.csi.estoque.controller;

import br.csi.estoque.model.produto.Produto;
import br.csi.estoque.model.produto.ProdutoDTO;
import br.csi.estoque.model.categoria.Categoria;
import br.csi.estoque.service.ProdutoService;
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
@RequestMapping("/produto")
@Tag(name = "Produtos", description = "Path relacionado a operações de produtos")
public class ProdutoController {

    private final ProdutoService service;

    public ProdutoController(ProdutoService service) {
        this.service = service;
    }

    @Operation(summary = "Listar todos os produtos", description = "Retorna uma lista de todos os produtos registrados.")
    @GetMapping("/listar")
    public List<ProdutoDTO> listar() {
        List<Produto> produtos = this.service.listar();
        return produtos.stream()
                .map(ProdutoDTO::new)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Buscar produto por UUID", description = "Retorna um produto específico pelo seu UUID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto encontrado"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    @GetMapping("/{uuid}")
    public ResponseEntity<ProdutoDTO> buscar(
            @Parameter(description = "UUID do produto a ser buscado", required = true)
            @PathVariable String uuid) {
        Optional<Produto> produto = this.service.getProdutoUUID(uuid);
        return produto.map(p -> ResponseEntity.ok(new ProdutoDTO(p)))
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Salvar novo produto", description = "Cadastra um novo produto.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Produto cadastrado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProdutoDTO.class))),
            @ApiResponse(responseCode = "400", description = "Erro na validação dos dados")
    })
    @PostMapping
    public ResponseEntity<ProdutoDTO> salvar(@RequestBody @Valid Produto produto, UriComponentsBuilder uriBuilder) {
        this.service.salvar(produto);
        URI uri = uriBuilder.path("/produto/{uuid}").buildAndExpand(produto.getUuid()).toUri();
        return ResponseEntity.created(uri).body(new ProdutoDTO(produto));
    }

    @Operation(summary = "Atualizar produto", description = "Atualiza as informações de um produto existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Produto atualizado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProdutoDTO.class))),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    @PutMapping
    public ResponseEntity<ProdutoDTO> atualizarUUID(@RequestBody @Valid Produto produto) {
        this.service.atualizarUUID(produto);
        return ResponseEntity.ok(new ProdutoDTO(produto));
    }

    @Operation(summary = "Adicionar Categoria", description = "Adiciona uma categoria a um produto.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Categoria adicionada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Produto ou categoria não encontrado")
    })
    @Transactional
    @PutMapping("/atribuir/{uuid}")
    public ResponseEntity<Void> atribuirCategoria(@PathVariable String uuid, @RequestBody Categoria categoria) {
        this.service.atribuirCategoria(uuid, categoria);
        return ResponseEntity.noContent().build();
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
