package br.com.softhouse.dende.modules.usuarios.repositories;

import br.com.softhouse.dende.modules.usuarios.model.Usuario;
import br.com.softhouse.dende.shared.repositories.RepositorioBase;

public class UsuarioRepositorio extends RepositorioBase<Usuario> {

    private static UsuarioRepositorio instance = new UsuarioRepositorio();

    private UsuarioRepositorio() {}

    public static UsuarioRepositorio getInstance() {
        return instance;
    }

    public Usuario cadastrarUsuario(Usuario usuario) {
        return salvar(usuario);
    }

    public Usuario atualizarUsuario(Long usuarioId, Usuario usuario) {
        entidades.replace(usuarioId, usuario);
        return usuario;
    }

    public Usuario buscarUsuarioPorEmail(String email) {
        if (email == null) {
            return null;
        }

        return entidades
                .values()
                .stream()
                .filter(usuario ->
                        usuario.getEmail() != null &&
                                usuario.getEmail().equalsIgnoreCase(email)
                )
                .findFirst()
                .orElse(null);
    }
}