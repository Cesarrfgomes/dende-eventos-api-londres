package br.com.softhouse.dende.controllers;

import br.com.dende.softhouse.annotations.Controller;
import br.com.dende.softhouse.annotations.request.*;
import br.com.dende.softhouse.process.route.ResponseEntity;
import br.com.softhouse.dende.dto.ErroDTO;
import br.com.softhouse.dende.dto.EventoListagemResponseDTO;
import br.com.softhouse.dende.dto.ReativarUsuarioRequestDTO;
import br.com.softhouse.dende.dto.UsuarioPerfilResponseDTO;
import br.com.softhouse.dende.exceptions.NotFoundException;
import br.com.softhouse.dende.model.Evento;
import br.com.softhouse.dende.model.Usuario;
import br.com.softhouse.dende.repositories.EventoRepositorio;
import br.com.softhouse.dende.repositories.UsuarioRepositorio;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Controller
@RequestMapping(path = "/usuarios")
public class UsuarioController {

    private final UsuarioRepositorio usuarioRepositorio;
    private final EventoRepositorio eventoRepositorio;

    public UsuarioController() {
        this.usuarioRepositorio = UsuarioRepositorio.getInstance();
        this.eventoRepositorio = EventoRepositorio.getInstance();
    }

    @PostMapping
    public ResponseEntity<Object> cadastroUsuario(@RequestBody Usuario usuario) {

        Usuario usuarioExiste = this.usuarioRepositorio.buscarUsuarioPorEmail(usuario.getEmail());

        if (usuarioExiste != null) {
            return ResponseEntity.status(400, new ErroDTO("O email já está em uso por outro usuário"));
        }

        Usuario novoUsuario = this.usuarioRepositorio.cadastrarUsuario(usuario);

        return ResponseEntity.ok("Usuario " + novoUsuario.getEmail() + " e ID " + novoUsuario.getId() + " registrado com sucesso!");
    }

    @PutMapping(path = "/{usuarioId}")
    public ResponseEntity<Object> atualizarUsuario(@PathVariable(parameter = "usuarioId") long usuarioId, @RequestBody Usuario usuario) {
        Usuario usuarioExiste = this.usuarioRepositorio.buscarUsuarioPorId(usuarioId);

        if (usuarioExiste == null) {
            return ResponseEntity.status(404, new ErroDTO("Usuário não encontrado."));
        }

        if (!Objects.equals(usuarioExiste.getEmail(), usuario.getEmail())) {
            return ResponseEntity.status(400, new ErroDTO("Não é permitido alterar o email do usuário."));
        }

        this.usuarioRepositorio.atualizarUsuario(usuarioExiste.getId(), usuario);

        return ResponseEntity.status(204, null);
    }
  
    @GetMapping(path = "/{usuarioId}")
    public ResponseEntity<Object> visualizarPerfil(@PathVariable(parameter = "usuarioId") long usuarioId) {
        Usuario usuarioExiste = this.usuarioRepositorio.buscarUsuarioPorId(usuarioId);

        if (usuarioExiste == null) {
            return ResponseEntity.status(404, new ErroDTO("Usuário não encontrado."));
        }

        UsuarioPerfilResponseDTO response = new UsuarioPerfilResponseDTO(usuarioExiste);

        return ResponseEntity.ok(response);
    }

    @PatchMapping(path = "/{usuarioId}/desativar")
    public ResponseEntity<String> desativarUsuario(@PathVariable(parameter = "usuarioId") long usuarioId) {

        try {
            Usuario usuarioExiste = this.usuarioRepositorio.buscarUsuarioPorId(usuarioId);

            if (Boolean.FALSE.equals(usuarioExiste.getIsAtivo())) {
                return ResponseEntity.status(400, "Usuário já está inativo.");
            }

            usuarioExiste.setIsAtivo(false);

            return ResponseEntity.status(204, null);
        }catch (NotFoundException e) {
            return ResponseEntity.status(404, e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500, e.getMessage());
        }
    }

    @PatchMapping(path = "/reativar")
    public ResponseEntity<String> reativarUsuario(@RequestBody ReativarUsuarioRequestDTO request) {
        Usuario usuarioExiste = this.usuarioRepositorio.buscarUsuarioPorEmail(request.email());

        if (usuarioExiste == null) {
            return ResponseEntity.status(404, "Usuário não encontrado.");
        }

        if (request.email() == null || request.email().isBlank() ||
                request.senha() == null || request.senha().isBlank()) {

            return ResponseEntity.status(400, "Email e senha são obrigatórios.");
        }

        if (!usuarioExiste.getSenha().equals(request.senha())) {
            return ResponseEntity.status(401, "Senha incorreta.");
        }

        if (usuarioExiste.getIsAtivo()) {
            return ResponseEntity.status(400, "Usuário já está ativo.");
        }


        usuarioExiste.setIsAtivo(true);

        return ResponseEntity.ok("Usuário reativado com sucesso.");
    }

    @GetMapping(path = "/{usuarioId}/feed")
    public ResponseEntity<Object> visualizarFeedEventos(@PathVariable(parameter = "usuarioId") long usuarioId) {

        Usuario usuarioExiste = this.usuarioRepositorio.buscarUsuarioPorId(usuarioId);

        if (usuarioExiste == null) {
            return ResponseEntity.status(404, new ErroDTO("Usuário não encontrado."));
        }

        List<Evento> eventosDisponiveis = this.eventoRepositorio.buscarFeedEventos();

        List<EventoListagemResponseDTO> response = eventosDisponiveis.stream()
                .map(EventoListagemResponseDTO.class::cast)
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }
}
