package br.com.softhouse.dende.modules.usuarios.controllers;

import br.com.dende.softhouse.annotations.Controller;
import br.com.dende.softhouse.annotations.request.*;
import br.com.dende.softhouse.process.route.ResponseEntity;
import br.com.softhouse.dende.exceptions.ErroDTO;
import br.com.softhouse.dende.modules.eventos.dto.EventoListagemResponseDTO;
import br.com.softhouse.dende.modules.usuarios.dto.AtualizarUsuarioRequestDTO;
import br.com.softhouse.dende.modules.usuarios.dto.CriarUsuarioRequestDTO;
import br.com.softhouse.dende.modules.usuarios.dto.ReativarUsuarioRequestDTO;
import br.com.softhouse.dende.modules.usuarios.dto.UsuarioDTO;
import br.com.softhouse.dende.exceptions.NotFoundException;
import br.com.softhouse.dende.modules.eventos.model.Evento;
import br.com.softhouse.dende.modules.usuarios.model.Usuario;
import br.com.softhouse.dende.modules.eventos.repositories.EventoRepositorio;
import br.com.softhouse.dende.modules.usuarios.repositories.UsuarioRepositorio;
import br.com.softhouse.dende.modules.usuarios.services.UsuarioService;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Controller
@RequestMapping(path = "/usuarios")
public class UsuarioController {

    private final UsuarioRepositorio usuarioRepositorio;
    private final EventoRepositorio eventoRepositorio;
    private final UsuarioService usuarioService;

    public UsuarioController() {
        this.usuarioRepositorio = UsuarioRepositorio.getInstance();
        this.eventoRepositorio = EventoRepositorio.getInstance();
        this.usuarioService = new UsuarioService();
    }

    @PostMapping
    public ResponseEntity<Object> cadastroUsuario(@RequestBody CriarUsuarioRequestDTO dto) {

        Usuario novoUsuario = this.usuarioService.criarUsuario(dto);

        return ResponseEntity.ok("Usuario " + novoUsuario.getEmail() + " e ID " + novoUsuario.getId() + " registrado com sucesso!");
    }

    @PutMapping(path = "/{usuarioId}")
    public ResponseEntity<Object> atualizarUsuario(@PathVariable(parameter = "usuarioId") long usuarioId, @RequestBody AtualizarUsuarioRequestDTO dto) {

        this.usuarioService.atualizarUsuario(usuarioId, dto);

        return ResponseEntity.status(204, null);
    }
  
    @GetMapping(path = "/{usuarioId}")
    public ResponseEntity<Object> visualizarPerfil(@PathVariable(parameter = "usuarioId") long usuarioId) {

        UsuarioDTO response = this.usuarioService.visualizarPerfil(usuarioId);

        return ResponseEntity.ok(response);
    }

    @PatchMapping(path = "/{usuarioId}/desativar")
    public ResponseEntity<String> desativarUsuario(@PathVariable(parameter = "usuarioId") long usuarioId) {

        try {
            this.usuarioService.desativarUsuario(usuarioId);

            return ResponseEntity.status(204, null);
        }catch (NotFoundException e) {
            return ResponseEntity.status(404, e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500, e.getMessage());
        }
    }

    @PatchMapping(path = "/reativar")
    public ResponseEntity<String> reativarUsuario(@RequestBody ReativarUsuarioRequestDTO request) {

        this.usuarioService.reativarUsuario(request);

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
