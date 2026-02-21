package br.com.softhouse.dende.controllers;

import br.com.dende.softhouse.annotations.Controller;
import br.com.dende.softhouse.annotations.request.*;
import br.com.dende.softhouse.process.route.ResponseEntity;
import br.com.softhouse.dende.dto.AtualizarEventoRequest;
import br.com.softhouse.dende.dto.Erro;
import br.com.softhouse.dende.dto.ReativarUsuarioRequest;
import br.com.softhouse.dende.dto.OrganizadorPerfilResponse;
import br.com.softhouse.dende.model.Empresa;
import br.com.softhouse.dende.model.Evento;
import br.com.softhouse.dende.model.Organizador;
import br.com.softhouse.dende.model.Usuario;
import br.com.softhouse.dende.repositories.EventoRepositorio;
import br.com.softhouse.dende.repositories.OrganizadorRepositorio;

import java.util.Objects;

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
        Organizador organizadorExiste = this.organizadorRepositorio.buscarOrganizadorPorEmail(organizador.getEmail());

        if (organizadorExiste != null) {
            return ResponseEntity.status(400, new Erro("Um usuário com esse email já está cadastrado."));
        }

        if (
                (organizador.getEmpresa().getCnpj() != null && (organizador.getEmpresa().getNomeFantasia() == null || organizador.getEmpresa().getRazaoSocial() == null)) ||
                        (organizador.getEmpresa().getNomeFantasia() != null && (organizador.getEmpresa().getCnpj() == null || organizador.getEmpresa().getRazaoSocial() == null)) ||
                        (organizador.getEmpresa().getRazaoSocial() != null && (organizador.getEmpresa().getNomeFantasia() == null || organizador.getEmpresa().getCnpj() == null))
        ) {
            return ResponseEntity.status(400, new Erro("Ao informar o CNPj, é necessário informar os campos: Nome Fantasia e Razão Social"));
        }

        var novoOrganizador = this.organizadorRepositorio.cadastrarOrganizador(organizador);

        return ResponseEntity.status(201, novoOrganizador);
    }

    @PutMapping(path = "/{organizadorId}")
    public ResponseEntity<Object> atualizarOrganizador(@PathVariable(parameter = "organizadorId") Long organizadorId, @RequestBody Organizador organizador) {
        Organizador organizadorExiste = this.organizadorRepositorio.buscarOrganizadorPorId(organizadorId);

        if (organizadorExiste == null) {
            return ResponseEntity.status(404, new Erro("Organizador não encontrado."));
        }

        if (!Objects.equals(organizadorExiste.getEmail(), organizador.getEmail())) {
            return ResponseEntity.status(400, new Erro("Não é permitido alterar o email do usuário."));
        }

        return ResponseEntity.status(204, null);
    }
  
    @GetMapping(path = "/{organizadorId}")
    public ResponseEntity<Object> visualizarPerfil(@PathVariable(parameter = "organizadorId") long organizadorId) {
        Organizador organizadorExiste = this.organizadorRepositorio.buscarOrganizadorPorId(organizadorId);

        if (organizadorExiste == null) {
            return ResponseEntity.status(404, new Erro("Usuário não encontrado."));
        }

        OrganizadorPerfilResponse response = new OrganizadorPerfilResponse(organizadorExiste);

        return ResponseEntity.ok(response);
    }

    @PostMapping(path = "/{organizadorId}/eventos")
    public ResponseEntity<Object> cadastrarEvento(@PathVariable(parameter = "organizadorId") long organizadorId, @RequestBody Evento evento) {
        Organizador organizador = this.organizadorRepositorio.buscarOrganizadorPorId(organizadorId);

        if(organizador == null){
            return ResponseEntity.status(404, new Erro("Organizador não encontrado."));
        }

        evento.setOrganizadorId(organizadorId);

        Evento novoEnvento = this.eventoRepositorio.cadastrarEvento(evento);

        return ResponseEntity.status(201, novoEnvento);
    }

    @PutMapping(path = "/{organizadorId}/eventos/{eventoId}")
    public ResponseEntity<Object> atualizarEvento(@PathVariable(parameter = "organizadorId") long organizadorId, @PathVariable(parameter = "eventoId") long  eventoId, @RequestBody AtualizarEventoRequest evento) {
        Evento eventoExiste = this.eventoRepositorio.buscarEventoPorId(eventoId);

        if (eventoExiste == null) {
            return ResponseEntity.status(404, new Erro("Evento não encontrado."));
        }

        if(eventoExiste.getOrganizadorId() != organizadorId){
            return ResponseEntity.status(401, new Erro("Usuário sem permissão para alterar o evento."));
        }


        this.eventoRepositorio.atualizarEvento(eventoId, eventoExiste);

        return ResponseEntity.status(204, null);
    }

    @PatchMapping(path = "/{organizadorId}/eventos/{eventoId}/ativar")
    public ResponseEntity<Object> ativarEvento(@PathVariable(parameter = "organizadorId") long organizadorId, @PathVariable(parameter = "eventoId") long eventoId) {
        Evento evento = this.eventoRepositorio.buscarEventoPorId(eventoId);

        if(evento == null){
            return ResponseEntity.status(404, new Erro("Evento não encontrado."));
        }

        evento.setIsAtivo(true);
        this.eventoRepositorio.atualizarEvento(eventoId, evento);

        return ResponseEntity.ok(evento);
    }

    @PatchMapping(path = "/{organizadorId}/eventos/{eventoId}/desativar")
    public ResponseEntity<Object> desativarEvento(@PathVariable(parameter = "organizadorId") long organizadorId, @PathVariable(parameter = "eventoId") long eventoId) {
        Evento evento = this.eventoRepositorio.buscarEventoPorId(eventoId);

        if(evento == null){
            return ResponseEntity.status(404, new Erro("Evento não encontrado."));
        }

        evento.setIsAtivo(false);
        this.eventoRepositorio.atualizarEvento(eventoId, evento);

        return ResponseEntity.ok(evento);
    }


    @PutMapping(path = "/{organizadorId}/desativar")
    public ResponseEntity<String> desativarOrganizador(@PathVariable(parameter = "organizadorId") long organizadorId) {
        Organizador organizadorExiste = this.organizadorRepositorio.buscarOrganizadorPorId(organizadorId);

        if (organizadorExiste == null) {
            return ResponseEntity.status(404, "Organizador não encontrado.");
        }

        if (Boolean.FALSE.equals(organizadorExiste.getIsAtivo())) {
            return ResponseEntity.status(400, "Usuário já está inativo.");
        }

        if(Boolean.TRUE.equals(organizadorExiste.getHasEvento())) {
            return ResponseEntity.status(400, "O organizador tem evento ativo ou em execução.");
        }

        organizadorExiste.setIsAtivo(false);

        return ResponseEntity.status(204, null);

    }

    @PatchMapping(path = "/reativar")
    public ResponseEntity<String> reativarOrganizador(@RequestBody ReativarUsuarioRequest request) {
        Organizador organizadorExiste = this.organizadorRepositorio.buscarOrganizadorPorEmail(request.getEmail());

        if (organizadorExiste == null) {
            return ResponseEntity.status(404, "Usuário não encontrado.");
        }

        if (request.getEmail() == null || request.getEmail().isBlank() ||
                request.getSenha() == null || request.getSenha().isBlank()) {

            return ResponseEntity.status(400, "Email e senha são obrigatórios.");
        }

        if (!organizadorExiste.getSenha().equals(request.getSenha())) {
            return ResponseEntity.status(401, "Senha incorreta.");
        }

        if (organizadorExiste.getIsAtivo()) {
            return ResponseEntity.status(400, "Usuário já está ativo.");
        }


        organizadorExiste.setIsAtivo(true);

        return ResponseEntity.ok("Usuário reativado com sucesso.");
    }
}
