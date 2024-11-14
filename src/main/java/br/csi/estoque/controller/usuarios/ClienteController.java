package br.csi.estoque.controller.usuarios;

import br.csi.estoque.model.usuarios.cliente.Cliente;
import br.csi.estoque.service.usuarios.ClienteService;
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
@RequestMapping("/cliente")
@Tag(name = "Clientes", description = "Path relacionado a operações de clientes")
public class ClienteController {

    private final ClienteService service;

    public ClienteController(ClienteService service) {
        this.service = service;
    }

    @Operation(summary = "Listar todos os clientes", description = "Retorna uma lista de todos os clientes cadastrados.")
    @GetMapping("/listar")
    public List<Cliente> listar() {
        return this.service.listar();
    }

    @Operation(summary = "Buscar cliente por UUID", description = "Retorna um cliente específico pelo seu UUID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    })
    @GetMapping("/{uuid}")
    public ResponseEntity<Cliente> buscar(
            @Parameter(description = "UUID do cliente a ser buscado", required = true)
            @PathVariable String uuid) {
        Optional<Cliente> cliente = this.service.getClienteUUID(uuid);
        return cliente.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Salvar novo cliente", description = "Cadastra um novo cliente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cliente cadastrado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Cliente.class))),
            @ApiResponse(responseCode = "400", description = "Erro na validação dos dados")
    })
    @PostMapping
    public ResponseEntity<Cliente> salvar(@RequestBody @Valid Cliente cliente, UriComponentsBuilder uriBuilder) {
        this.service.salvar(cliente);
        URI uri = uriBuilder.path("/cliente/{uuid}").buildAndExpand(cliente.getUuid()).toUri();
        return ResponseEntity.created(uri).body(cliente);
    }

    @Operation(summary = "Atualizar cliente", description = "Atualiza as informações de um cliente existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Cliente atualizado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Cliente.class))),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    })
    @PutMapping
    public ResponseEntity<Cliente> atualizarUUID(@RequestBody @Valid Cliente cliente) {
        this.service.atualizarUUID(cliente);
        return ResponseEntity.ok(cliente);
    }

    @Operation(summary = "Deletar cliente", description = "Remove um cliente pelo seu UUID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Cliente deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    })
    @Transactional
    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deletar(
            @Parameter(description = "UUID do cliente a ser deletado", required = true)
            @PathVariable String uuid) {
        this.service.deletarUUID(uuid);
        return ResponseEntity.noContent().build();
    }
}
