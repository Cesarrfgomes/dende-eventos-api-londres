package br.com.softhouse.dende.controllers;

import br.com.dende.softhouse.annotations.Controller;
import br.com.dende.softhouse.annotations.request.*;
import br.com.dende.softhouse.process.route.ResponseEntity;
import br.com.softhouse.dende.dto.Erro;
import br.com.softhouse.dende.dto.ReativarUsuarioRequest;
import br.com.softhouse.dende.dto.UsuarioPerfilResponse;
import br.com.softhouse.dende.model.Usuario;
import br.com.softhouse.dende.repositories.UsuarioRepositorio;

import java.util.Objects;

@Controller
@RequestMapping(path = "/usuarios")
public class UsuarioController {

    private final UsuarioRepositorio usuarioRepositorio;

    public UsuarioController() {
        this.usuarioRepositorio = UsuarioRepositorio.getInstance();
    }

    @PostMapping
    public ResponseEntity<Object> cadastroUsuario(@RequestBody Usuario usuario) {

        Usuario usuarioExiste = this.usuarioRepositorio.buscarUsuarioPorEmail(usuario.getEmail());

        if (usuarioExiste != null) {
            return ResponseEntity.status(400, new Erro("O email já está em uso por outro usuário"));
        }

        Usuario novoUsuario = this.usuarioRepositorio.cadastrarUsuario(usuario);

        return ResponseEntity.ok("Usuario " + novoUsuario.getEmail() + " e ID " + novoUsuario.getId() + " registrado com sucesso!");
    }

    @PutMapping(path = "/{usuarioId}")
    public ResponseEntity<Object> atualizarUsuario(@PathVariable(parameter = "usuarioId") long usuarioId, @RequestBody Usuario usuario) {
        Usuario usuarioExiste = this.usuarioRepositorio.buscarUsuarioPorId(usuarioId);

        if (usuarioExiste == null) {
            return ResponseEntity.status(404, new Erro("Usuário não encontrado."));
        }

        if (!Objects.equals(usuarioExiste.getEmail(), usuario.getEmail())) {
            return ResponseEntity.status(400, new Erro("Não é permitido alterar o email do usuário."));
        }

        this.usuarioRepositorio.atualizarUsuario(usuarioExiste.getId(), usuario);

        return ResponseEntity.status(204, null);
    }
  
    @GetMapping(path = "/{usuarioId}")
    public ResponseEntity<Object> visualizarPerfil(@PathVariable(parameter = "usuarioId") long usuarioId) {
        Usuario usuarioExiste = this.usuarioRepositorio.buscarUsuarioPorId(usuarioId);

        if (usuarioExiste == null) {
            return ResponseEntity.status(404, new Erro("Usuário não encontrado."));
        }

        UsuarioPerfilResponse response = new UsuarioPerfilResponse(usuarioExiste);

        return ResponseEntity.ok(response);
    }

    @PutMapping(path = "/{usuarioId}/desativar")
    public ResponseEntity<String> desativarUsuario(@PathVariable(parameter = "usuarioId") long usuarioId) {
        Usuario usuarioExiste = this.usuarioRepositorio.buscarUsuarioPorId(usuarioId);

        if (usuarioExiste == null) {
            return ResponseEntity.status(404, "Usuário não encontrado.");
        }

        if (Boolean.FALSE.equals(usuarioExiste.getIsAtivo())) {
            return ResponseEntity.status(400, "Usuário já está inativo.");
        }

        usuarioExiste.setIsAtivo(false);

        return ResponseEntity.status(204, null);

    }

    @PatchMapping(path = "/reativar")
    public ResponseEntity<String> reativarUsuario(@RequestBody ReativarUsuarioRequest request) {
        Usuario usuarioExiste = this.usuarioRepositorio.buscarUsuarioPorEmail(request.getEmail());

        if (usuarioExiste == null) {
            return ResponseEntity.status(404, "Usuário não encontrado.");
        }

        if (request.getEmail() == null || request.getEmail().isBlank() ||
                request.getSenha() == null || request.getSenha().isBlank()) {

            return ResponseEntity.status(400, "Email e senha são obrigatórios.");
        }

        if (!usuarioExiste.getSenha().equals(request.getSenha())) {
            return ResponseEntity.status(401, "Senha incorreta.");
        }

        if (usuarioExiste.getIsAtivo()) {
            return ResponseEntity.status(400, "Usuário já está ativo.");
        }


        usuarioExiste.setIsAtivo(true);

        return ResponseEntity.ok("Usuário reativado com sucesso.");
    }
}
