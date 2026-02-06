package br.com.softhouse.dende.repositories;

import br.com.softhouse.dende.model.Organizador;

import java.util.HashMap;
import java.util.Map;

public class OrganizadorRepositorio {
    private static OrganizadorRepositorio instance = new OrganizadorRepositorio();
    private final Map<String, Organizador> organizadores;

    private OrganizadorRepositorio() {
        this.organizadores = new HashMap<>();
    }

    public static OrganizadorRepositorio getInstance() {
        return instance;
    }

    public void cadastrarOrganizador(Organizador organizador) {
        this.organizadores.put(organizador.getEmail(), organizador);
    }

    public Organizador buscarOrganizadorPorEmail(String email) {
        return organizadores.get(email);
    }
}
