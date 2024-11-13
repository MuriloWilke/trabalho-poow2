package br.csi.estoque.service.usuarios;

import br.csi.estoque.model.usuarios.usuario.DadosUsuario;
import br.csi.estoque.model.usuarios.usuario.Usuario;
import br.csi.estoque.model.usuarios.usuario.UsuarioRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UsuarioService {

    private final UsuarioRepository repository;

    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    public void salvar(Usuario usuario) {
        this.repository.save(usuario);
    }

    public List<Usuario> listar() {
        return this.repository.findAll();
    }

    public Optional<Usuario> getUsuarioUUID(String uuid) {
        UUID uuidFormatado = UUID.fromString(uuid);
        return this.repository.findByUuid(uuidFormatado);
    }

    public void atualizarUUID(Usuario usuario) {
        Optional<Usuario> optionalUsuario = this.repository.findByUuid(usuario.getUuid());
        if (optionalUsuario.isPresent()) {
            Usuario u = optionalUsuario.get();
            u.setNome(usuario.getNome());
            u.setEmail(usuario.getEmail());
            u.setSenha(usuario.getSenha());
            this.repository.save(u);
        } else {
            throw new RuntimeException("Usuário não encontrado");
        }
    }

    public void deletarUUID(String uuid) {
        this.repository.deleteByUuid(UUID.fromString(uuid));
    }

    public void cadastrar(Usuario usuario){
        usuario.setSenha(new BCryptPasswordEncoder().encode(usuario.getSenha()));
        this.repository.save(usuario);
    }

    public DadosUsuario findUsuario(Long id){
        Usuario usuario = this.repository.getReferenceById(id);
        return new DadosUsuario(usuario);
    }

    public List<DadosUsuario> findAllUsuarios(){
        return this.repository.findAll().stream().map(DadosUsuario::new).toList();
    }
}
