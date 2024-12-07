package br.csi.estoque.service;

import br.csi.estoque.model.usuario.Usuario;
import br.csi.estoque.model.usuario.UsuarioRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AutenticacaoService implements UserDetailsService {

    private final UsuarioRepository repository;

    public AutenticacaoService(UsuarioRepository repository){
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = this.repository.findByEmail(email);
        if(usuario == null) {
            throw new UsernameNotFoundException("Usuário ou senha incorretos");
        } else {
            String permissao = usuario.getPermissao();

            UserDetails user = User.withUsername(usuario.getEmail())
                    .password(usuario.getSenha())
                    .authorities(new SimpleGrantedAuthority(permissao))
                    .build();
            return user;
        }
    }

}
