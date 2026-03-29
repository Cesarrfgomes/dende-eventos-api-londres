package br.com.softhouse.dende.modules.usuarios.services;

import br.com.softhouse.dende.exceptions.NotFoundException;
import br.com.softhouse.dende.modules.usuarios.dto.AtualizarUsuarioRequestDTO;
import br.com.softhouse.dende.modules.usuarios.dto.CriarUsuarioRequestDTO;
import br.com.softhouse.dende.modules.usuarios.dto.ReativarUsuarioRequestDTO;
import br.com.softhouse.dende.modules.usuarios.dto.UsuarioDTO;
import br.com.softhouse.dende.modules.usuarios.mappers.UsuarioMapper;
import br.com.softhouse.dende.modules.usuarios.model.Usuario;
import br.com.softhouse.dende.modules.usuarios.repositories.UsuarioRepositorio;

import java.util.Objects;

public class UsuarioService {

    private final UsuarioRepositorio usuarioRepositorio;
    private final UsuarioMapper usuarioMapper;

    public UsuarioService() {
        this.usuarioRepositorio = UsuarioRepositorio.getInstance();
        this.usuarioMapper = new UsuarioMapper();
    }

    public Usuario criarUsuario(CriarUsuarioRequestDTO dto) {
        Usuario usuarioExiste = this.usuarioRepositorio.buscarUsuarioPorEmail(dto.email());

        if (usuarioExiste != null) {
            throw new RuntimeException("O email já está em uso por outro usuário");
        }

        Usuario usuario = new Usuario(dto);

        var novoUsuario = this.usuarioRepositorio.cadastrarUsuario(usuario);

        System.out.println(novoUsuario);

        return novoUsuario;
    }

    public Usuario atualizarUsuario(long usuarioId, AtualizarUsuarioRequestDTO dto) {
        Usuario usuarioExiste = this.usuarioRepositorio.buscarPorId(usuarioId);

        if (usuarioExiste == null) {
            throw new NotFoundException("Usuário não encontrado");
        }

        if (!Objects.equals(usuarioExiste.getEmail(), dto.email())) {
            throw new IllegalArgumentException("Email são iguais.");
        }

        usuarioExiste.setNome(dto.nome());
        usuarioExiste.setSenha(dto.senha());
        usuarioExiste.setDataNascimento(dto.dataNascimento());
        usuarioExiste.setSexo(dto.sexo());
        usuarioExiste.setEmail(dto.email());

        return this.usuarioRepositorio.atualizarUsuario(usuarioId, usuarioExiste);
    }

    public Usuario buscarUsuarioPorId(long usuarioId) {
        Usuario usuarioExiste = this.usuarioRepositorio.buscarPorId(usuarioId);

        if (usuarioExiste == null) {
            throw new NotFoundException("Usuário não encontrado");
        }

        return usuarioExiste;
    }

    public UsuarioDTO visualizarPerfil(long usuarioId) {
        Usuario usuarioExiste = this.buscarUsuarioPorId(usuarioId);

        return usuarioMapper.toDto(usuarioExiste);
    }

    public void desativarUsuario(long usuarioId) {
        Usuario usuarioExiste = this.usuarioRepositorio.buscarPorId(usuarioId);

        if (Boolean.FALSE.equals(usuarioExiste.getIsAtivo())) {
            throw new IllegalStateException("Usuário já está inativo.");
        }

        usuarioExiste.setIsAtivo(false);
    }

    public void reativarUsuario(ReativarUsuarioRequestDTO request) {
        Usuario usuarioExiste = this.usuarioRepositorio.buscarUsuarioPorEmail(request.email());

        if (usuarioExiste == null) {
            throw new NotFoundException("Usuário não encontrado");
        }

        if (request.email() == null || request.email().isBlank() ||
                request.senha() == null || request.senha().isBlank()) {

            throw new IllegalArgumentException("Email e senha são obrigatórios.");
        }

        if (!usuarioExiste.getSenha().equals(request.senha())) {
            throw new SecurityException("Senha incorreta.");
        }

        if (usuarioExiste.getIsAtivo()) {
            throw new IllegalStateException("Usuário já está ativo.");
        }

        usuarioExiste.setIsAtivo(true);
    }
}
