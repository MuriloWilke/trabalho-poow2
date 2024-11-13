package br.csi.estoque.model.usuarios.usuario;

public record DadosUsuario(Long id, String email, int permissao) {

    public DadosUsuario(Usuario usuario) { this(usuario.getId(), usuario.getEmail(), usuario.getPermissao());}

}
