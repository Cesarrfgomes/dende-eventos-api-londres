package br.com.softhouse.dende.repositories;

import br.com.softhouse.dende.model.Usuario;

import java.util.HashMap;
import java.util.Map;

public class UsuarioRepositorio {
    private static UsuarioRepositorio instance = new UsuarioRepositorio();
    private final Map<String, Usuario> usuariosComum;

    private UsuarioRepositorio() {
        this.usuariosComum = new HashMap<>();
    }

    public static UsuarioRepositorio getInstance() {
        return instance;
    }

    public void salvarUsuario(Usuario usuario) {
        this.usuariosComum.put(usuario.getEmail(), usuario);
    }

    public Usuario buscarUsuarioPorEmail(String email) {
        return usuariosComum.get(email);
    }
}
