package br.com.softhouse.dende.controllers;

import br.com.dende.softhouse.annotations.Controller;
import br.com.dende.softhouse.annotations.request.*;
import br.com.dende.softhouse.process.route.ResponseEntity;
import br.com.softhouse.dende.dto.ReativarUsuarioRequest;
import br.com.softhouse.dende.model.Organizador;
import br.com.softhouse.dende.model.Usuario;
import br.com.softhouse.dende.repositories.OrganizadorRepositorio;

import java.util.Objects;

@Controller
@RequestMapping(path = "/organizadores")
public class OrganizadorController {

    private final OrganizadorRepositorio organizadorRepositorio;

    public OrganizadorController() {
        this.organizadorRepositorio = OrganizadorRepositorio.getInstance();
    }

    @PostMapping
    public ResponseEntity<String> cadastrarOrganizador(@RequestBody Organizador organizador) {
        Organizador organizadorExiste = this.organizadorRepositorio.buscarOrganizadorPorEmail(organizador.getEmail());

        if (organizadorExiste != null) {
            return ResponseEntity.status(400, "Um usuário com esse email já está cadastrado.");
        }

        if (
                (organizador.getCnpj() != null && (organizador.getNomeFantasia() == null || organizador.getRazaoSocial() == null)) ||
                        (organizador.getNomeFantasia() != null && (organizador.getCnpj() == null || organizador.getRazaoSocial() == null)) ||
                        (organizador.getRazaoSocial() != null && (organizador.getNomeFantasia() == null || organizador.getCnpj() == null))
        ) {
            return ResponseEntity.status(400, "Ao informar o CNPj, é necessário informar os campos: Nome Fantasia e Razão Social");
        }

        this.organizadorRepositorio.cadastrarOrganizador(organizador);

        return ResponseEntity.ok("Organizador cadastrado com sucesso.");
    }

    @PutMapping(path = "/{organizadorId}")
    public ResponseEntity<String> atualizarOrganizador(@PathVariable(parameter = "organizadorId") Long organizadorId, @RequestBody Organizador organizador) {
        Organizador organizadorExiste = this.organizadorRepositorio.buscarOrganizadorPorId(organizadorId);

        if (organizadorExiste == null) {
            return ResponseEntity.status(404, "Organizador não encontrado.");
        }

        if (!Objects.equals(organizadorExiste.getEmail(), organizador.getEmail())) {
            return ResponseEntity.status(400, "Não é permitido alterar o email do usuário.");
        }

        return ResponseEntity.status(204, null);
    }

    @PatchMapping(path = "/reativar")
    public ResponseEntity<String> reativarUsuario(@RequestBody ReativarUsuarioRequest request) {
        Organizador organizadorExiste = this.organizadorRepositorio.buscarOrganizadorPorEmail(request.getEmail());

        if (organizadorExiste == null) {
            return ResponseEntity.status(404, "Usuário não encontrado.");
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
