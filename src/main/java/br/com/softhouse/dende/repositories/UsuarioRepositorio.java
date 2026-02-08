package br.com.softhouse.dende.repositories;

import br.com.softhouse.dende.model.Usuario;

import java.util.HashMap;
import java.util.Map;

public class UsuarioRepositorio {
    private static UsuarioRepositorio instance = new UsuarioRepositorio();
    private final Map<Long, Usuario> usuariosComum;

    private Long contadorId;

    private UsuarioRepositorio() {
        this.usuariosComum = new HashMap<>();
        this.contadorId = 1L;
    }

    public static UsuarioRepositorio getInstance() {
        return instance;
    }

    public Usuario buscarUsuarioPorId(Long id) {
        return usuariosComum.get(id);
    }

    public Usuario cadastrarUsuario(Usuario usuario) {
        if (usuario.getId() == null) {
            usuario.setId(this.contadorId);
            this.contadorId++;
        }

        this.usuariosComum.put(usuario.getId(), usuario);

        return usuario;
    }

    public Usuario atualizarUsuario(Long usuarioId, Usuario usuario) {
        this.usuariosComum.replace(usuarioId, usuario);
        return usuario;
    }

    public Usuario buscarUsuarioPorEmail(String email) {
        if (email == null) {
            return null;
        }

        return usuariosComum
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
