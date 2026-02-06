package br.com.softhouse.dende.controllers;

import br.com.dende.softhouse.annotations.Controller;
import br.com.dende.softhouse.annotations.request.*;
import br.com.dende.softhouse.process.route.ResponseEntity;
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
    public ResponseEntity<String> cadastroUsuario(@RequestBody Usuario usuario) {

        Usuario usuarioExiste = this.usuarioRepositorio.buscarUsuarioPorEmail(usuario.getEmail());

        if (usuarioExiste != null) {
            return ResponseEntity.status(400, "O email já está em uso por outro usuário");
        }

        Usuario novoUsuario = this.usuarioRepositorio.cadastrarUsuario(usuario);

        return ResponseEntity.ok("Usuario " + novoUsuario.getEmail() + " e ID " + novoUsuario.getId() + " registrado com sucesso!");
    }

    @PutMapping(path = "/{usuarioId}")
    public ResponseEntity<String> alterarUsuario(@PathVariable(parameter = "usuarioId") long usuarioId, @RequestBody Usuario usuario) {
        Usuario  usuarioExiste = this.usuarioRepositorio.buscarUsuarioPorId(usuarioId);

        if(usuarioExiste == null) {
            return ResponseEntity.status(404, "Usuário não encontrado.");
        }

        if (!Objects.equals(usuarioExiste.getEmail(), usuario.getEmail())){
            return ResponseEntity.status(400, "Não é permitido alterar o email do usuário.");
        }

        this.usuarioRepositorio.atualizarUsuario(usuarioExiste.getId(), usuario);

        return ResponseEntity.ok("Usuario " + usuario.getEmail() + " do usuarioId = " + usuarioId + " alterado com sucesso!");
    }
}
