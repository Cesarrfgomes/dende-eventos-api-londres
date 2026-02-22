package br.com.softhouse.dende.controllers;

import br.com.dende.softhouse.annotations.Controller;
import br.com.dende.softhouse.annotations.request.*;
import br.com.dende.softhouse.process.route.ResponseEntity;
import br.com.softhouse.dende.dto.*;
import br.com.softhouse.dende.exceptions.BadRequestException;
import br.com.softhouse.dende.exceptions.NotFoundException;
import br.com.softhouse.dende.model.Evento;
import br.com.softhouse.dende.model.Organizador;
import br.com.softhouse.dende.repositories.EventoRepositorio;
import br.com.softhouse.dende.repositories.OrganizadorRepositorio;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping(path = "/organizadores")
public class OrganizadorController {

    private final OrganizadorRepositorio organizadorRepositorio;

    private final EventoRepositorio eventoRepositorio;

    public OrganizadorController() {
        this.organizadorRepositorio = OrganizadorRepositorio.getInstance();
        this.eventoRepositorio = EventoRepositorio.getInstance();
    }

    @PostMapping
    public ResponseEntity<Object> cadastrarOrganizador(@RequestBody Organizador organizador) {
        try {
            Organizador organizadorExiste = this.organizadorRepositorio.buscarOrganizadorPorEmail(organizador.getEmail());

            var novoOrganizador = this.organizadorRepositorio.cadastrarOrganizador(organizador);

            return ResponseEntity.status(201, novoOrganizador);
        } catch (BadRequestException e) {
            return ResponseEntity.status(400, e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500, e.getMessage());
        }
    }

    @PutMapping(path = "/{organizadorId}")
    public ResponseEntity<Object> atualizarOrganizador(@PathVariable(parameter = "organizadorId") Long organizadorId, @RequestBody Organizador organizador) {
        try {
            this.organizadorRepositorio.atualizarOrganizador(organizadorId, organizador);

            return ResponseEntity.status(204, null);
        } catch (NotFoundException e) {
            return ResponseEntity.status(404, new ErroDTO(e.getMessage()));
        } catch (BadRequestException e) {
            return ResponseEntity.status(400, new ErroDTO(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(500, new ErroDTO(e.getMessage()));
        }
    }

    @GetMapping(path = "/{organizadorId}")
    public ResponseEntity<Object> visualizarPerfil(@PathVariable(parameter = "organizadorId") long organizadorId) {
        try {
            Organizador organizadorExiste = this.organizadorRepositorio.buscarOrganizadorPorId(organizadorId);

            OrganizadorPerfilResponseDTO response = new OrganizadorPerfilResponseDTO(organizadorExiste);

            return ResponseEntity.ok(response);
        } catch (NotFoundException e) {
            return ResponseEntity.status(404, new ErroDTO(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(500, new ErroDTO(e.getMessage()));
        }
    }

    @PostMapping(path = "/{organizadorId}/eventos")
    public ResponseEntity<Object> cadastrarEvento(@PathVariable(parameter = "organizadorId") long organizadorId, @RequestBody Evento evento) {
        try {
            Organizador organizador = this.organizadorRepositorio.buscarOrganizadorPorId(organizadorId);

            evento.setOrganizadorId(organizadorId);

            Evento novoEnvento = this.eventoRepositorio.cadastrarEvento(evento);

            return ResponseEntity.status(201, novoEnvento);
        } catch (NotFoundException e) {
            return ResponseEntity.status(404, new ErroDTO(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(500, new ErroDTO(e.getMessage()));
        }
    }

    @PutMapping(path = "/{organizadorId}/eventos/{eventoId}")
    public ResponseEntity<Object> atualizarEvento(@PathVariable(parameter = "organizadorId") long organizadorId, @PathVariable(parameter = "eventoId") long eventoId, @RequestBody AtualizarEventoRequestDTO evento) {

        try {
            Evento eventoExiste = this.eventoRepositorio.buscarEventoPorId(eventoId);

            if (eventoExiste.getOrganizadorId() != organizadorId) {
                return ResponseEntity.status(401, new ErroDTO("Usuário sem permissão para alterar o evento."));
            }

            this.eventoRepositorio.atualizarEvento(eventoId, eventoExiste);

            return ResponseEntity.status(204, null);
        } catch (NotFoundException e) {
            return ResponseEntity.status(404, new ErroDTO(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(500, new ErroDTO(e.getMessage()));
        }
    }

    @PatchMapping(path = "/{organizadorId}/eventos/{eventoId}/ativar")
    public ResponseEntity<Object> ativarEvento(@PathVariable(parameter = "organizadorId") long organizadorId, @PathVariable(parameter = "eventoId") long eventoId) {
        try {
            Evento evento = this.eventoRepositorio.buscarEventoPorId(eventoId);

            evento.setIsAtivo(true);
            this.eventoRepositorio.atualizarEvento(eventoId, evento);

            return ResponseEntity.ok(evento);
        } catch (NotFoundException e) {
            return ResponseEntity.status(404, new ErroDTO(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(500, new ErroDTO(e.getMessage()));
        }
    }

    @PatchMapping(path = "/{organizadorId}/eventos/{eventoId}/desativar")
    public ResponseEntity<Object> desativarEvento(@PathVariable(parameter = "organizadorId") long organizadorId, @PathVariable(parameter = "eventoId") long eventoId) {
        try {
            Evento evento = this.eventoRepositorio.buscarEventoPorId(eventoId);

            evento.setIsAtivo(false);
            this.eventoRepositorio.atualizarEvento(eventoId, evento);

            return ResponseEntity.ok(evento);
        } catch (NotFoundException e) {
            return ResponseEntity.status(404, new ErroDTO(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(500, new ErroDTO(e.getMessage()));
        }
    }


    @PutMapping(path = "/{organizadorId}/desativar")
    public ResponseEntity<String> desativarOrganizador(@PathVariable(parameter = "organizadorId") long organizadorId) {
        try {
            Organizador organizadorExiste = this.organizadorRepositorio.buscarOrganizadorPorId(organizadorId);

            if (Boolean.FALSE.equals(organizadorExiste.getIsAtivo())) {
                return ResponseEntity.status(400, "Usuário já está inativo.");
            }

            if (Boolean.TRUE.equals(organizadorExiste.getHasEvento())) {
                return ResponseEntity.status(400, "O organizador tem evento ativo ou em execução.");
            }

            organizadorExiste.setIsAtivo(false);

            return ResponseEntity.status(204, null);
        } catch (NotFoundException e) {
            return ResponseEntity.status(404, e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500, e.getMessage());
        }
    }

    @PatchMapping(path = "/reativar")
    public ResponseEntity<String> reativarOrganizador(@RequestBody ReativarUsuarioRequestDTO request) {
        try {
            Organizador organizadorExiste = this.organizadorRepositorio.buscarOrganizadorPorEmail(request.email());

            if (organizadorExiste == null) {
                return ResponseEntity.status(404, "Usuário não encontrado.");
            }

            if (request.email() == null || request.email().isBlank() || request.senha() == null || request.senha().isBlank()) {

                return ResponseEntity.status(400, "Email e senha são obrigatórios.");
            }

            if (!organizadorExiste.getSenha().equals(request.senha())) {
                return ResponseEntity.status(401, "Senha incorreta.");
            }

            if (organizadorExiste.getIsAtivo()) {
                return ResponseEntity.status(400, "Usuário já está ativo.");
            }


            organizadorExiste.setIsAtivo(true);

            return ResponseEntity.ok("Usuário reativado com sucesso.");
        } catch (NotFoundException e) {
            return ResponseEntity.status(404, e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500, e.getMessage());
        }
    }

    @GetMapping(path = "/{organizadorId}/eventos")
    public ResponseEntity<Object> listarEventosDoOrganizador(@PathVariable(parameter = "organizadorId") long organizadorId) {
        try {
            Organizador organizadorExiste = this.organizadorRepositorio.buscarOrganizadorPorId(organizadorId);

            List<Evento> eventosDoOrganizador = this.eventoRepositorio.buscarEventosPorOrganizador(organizadorId);

            if (eventosDoOrganizador.isEmpty()) {
                return ResponseEntity.ok(Collections.emptyList());
            }

            List<EventoListagemResponseDTO> response = eventosDoOrganizador.stream().map(EventoListagemResponseDTO.class::cast).collect(Collectors.toList());

            return ResponseEntity.ok(response);
        } catch (NotFoundException e) {
            return ResponseEntity.status(404, new ErroDTO(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(500, new ErroDTO(e.getMessage()));
        }

    }
}
