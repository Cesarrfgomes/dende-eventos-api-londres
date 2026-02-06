package br.com.softhouse.dende.controllers;

import br.com.dende.softhouse.annotations.Controller;
import br.com.dende.softhouse.annotations.request.PostMapping;
import br.com.dende.softhouse.annotations.request.RequestBody;
import br.com.dende.softhouse.annotations.request.RequestMapping;
import br.com.dende.softhouse.process.route.ResponseEntity;
import br.com.softhouse.dende.model.Organizador;
import br.com.softhouse.dende.repositories.Repositorio;

@Controller
@RequestMapping(path = "/organizadores")
public class OrganizadorController {

    private final Repositorio repositorio;

    public OrganizadorController() {
        this.repositorio = Repositorio.getInstance();
    }

    @PostMapping
    public ResponseEntity<String> cadastrarOrganizador(@RequestBody Organizador organizador) {
        Organizador organizadorExiste = repositorio.buscarOrganizadorPorEmail(organizador.getEmail());

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

        repositorio.cadastrarOrganizador(organizador);

        return ResponseEntity.ok("Organizador cadastrado com sucesso.");
    }
}
