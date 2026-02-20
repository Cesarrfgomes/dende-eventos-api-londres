package br.com.softhouse.dende.controllers;

import br.com.dende.softhouse.annotations.Controller;
import br.com.dende.softhouse.annotations.request.*;
import br.com.dende.softhouse.process.route.ResponseEntity;
import br.com.softhouse.dende.dto.Erro;
import br.com.softhouse.dende.dto.OrganizadorPerfilResponse;
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
                (organizador.getCnpj() != null && (organizador.getNomeFantasia() == null || organizador.getRazaoSocial() == null)) ||
                        (organizador.getNomeFantasia() != null && (organizador.getCnpj() == null || organizador.getRazaoSocial() == null)) ||
                        (organizador.getRazaoSocial() != null && (organizador.getNomeFantasia() == null || organizador.getCnpj() == null))
        ) {
            return ResponseEntity.status(400, new Erro("Ao informar o CNPj, é necessário informar os campos: Nome Fantasia e Razão Social"));
        }

        this.organizadorRepositorio.cadastrarOrganizador(organizador);

        return ResponseEntity.status(201, organizador);
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

        Evento novoEnvento = this.eventoRepositorio.cadastrarEvento(evento);

        return ResponseEntity.status(201, novoEnvento);
    }

}
