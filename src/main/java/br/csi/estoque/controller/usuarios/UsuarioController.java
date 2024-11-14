package br.csi.estoque.controller.usuarios;

import br.csi.estoque.model.usuarios.usuario.DadosUsuario;
import br.csi.estoque.service.usuarios.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import br.csi.estoque.model.usuarios.usuario.Usuario;
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
@RequestMapping("/usuario")
@Tag(name = "Usuarios", description = "Path relacionado a operações de usuários")
public class UsuarioController {

    private final UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    @Operation(summary = "Listar todos os usuários", description = "Retorna uma lista de todos os usuários cadastrados.")
    @GetMapping("/listar")
    public List<Usuario> listar() {
        return this.service.listar();
    }

    @Operation(summary = "Listar os dados específicos dos usuários", description = "Retorna uma lista de dados específicos os usuários cadastrados.")
    @GetMapping("/listarDU")
    public List<DadosUsuario> listarDU() {
        return this.service.findAllUsuarios();
    }

    @Operation(summary = "Buscar usuário por UUID", description = "Retorna um usuário específico pelo seu UUID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário encontrado"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @GetMapping("/{uuid}")
    public ResponseEntity<Usuario> buscar(
            @Parameter(description = "UUID do usuário a ser buscado", required = true)
            @PathVariable String uuid) {
        Optional<Usuario> usuario = this.service.getUsuarioUUID(uuid);
        return usuario.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Buscar usuário por ID", description = "Retorna um usuário específico pelo seu ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário encontrado"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @GetMapping("/{id}")
    public DadosUsuario findById(@PathVariable Long id){

        return this.service.findUsuario(id);

    }

    @Operation(summary = "Salvar novo usuário", description = "Cadastra um novo usuário.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário cadastrado com sucesso",
                content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Usuario.class))),
            @ApiResponse(responseCode = "400", description = "Erro na validação dos dados")
    })
    @PostMapping
    @Transactional
    public ResponseEntity<Usuario> salvar(@RequestBody @Valid Usuario usuario, UriComponentsBuilder uriBuilder) {
        this.service.salvar(usuario);
        URI uri = uriBuilder.path("/usuario/{uuid}").buildAndExpand(usuario.getUuid()).toUri();
        return ResponseEntity.created(uri).body(usuario);
    }

    @Operation(summary = "Atualizar usuário", description = "Atualiza as informações de um usuário existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Usuário atualizado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Usuario.class))),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @PutMapping
    public ResponseEntity<Usuario> atualizarUUID(@RequestBody @Valid Usuario usuario) {
        this.service.atualizarUUID(usuario);
        return ResponseEntity.ok(usuario);
    }

    @Operation(summary = "Deletar usuário", description = "Remove um usuário pelo seu UUID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Usuário deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @Transactional
    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deletar(
            @Parameter(description = "UUID do usuário a ser deletado", required = true)
            @PathVariable String uuid) {
        this.service.deletarUUID(uuid);
        return ResponseEntity.noContent().build();
    }
}
