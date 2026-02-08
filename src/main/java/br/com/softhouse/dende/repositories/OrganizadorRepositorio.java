package br.com.softhouse.dende.repositories;

import br.com.softhouse.dende.model.Organizador;

import java.util.HashMap;
import java.util.Map;

public class OrganizadorRepositorio {
    private static OrganizadorRepositorio instance = new OrganizadorRepositorio();
    private final Map<Long, Organizador> organizadores;

    private Long contadorId;

    private OrganizadorRepositorio() {
        this.organizadores = new HashMap<>();
        this.contadorId = 1L;
    }

    public static OrganizadorRepositorio getInstance() {
        return instance;
    }

    public Organizador buscarOrganizadorPorId(Long id) {
        return organizadores.get(id);
    }

    public void cadastrarOrganizador(Organizador organizador) {
        if (organizador.getId() == null) {
            organizador.setId(this.contadorId);
            this.contadorId++;
        }
        this.organizadores.put(organizador.getId(), organizador);
    }

    public Organizador atualizarOrganizador(Long organizadorId, Organizador organizador) {
        this.organizadores.replace(organizadorId, organizador);

        return organizador;
    }

    public Organizador buscarOrganizadorPorEmail(String email) {

        if (email == null) {
            return null;
        }

        return organizadores
                .values()
                .stream()
                .filter(organizador ->
                        organizador.getEmail() != null &&
                                organizador.getEmail().equalsIgnoreCase(email)
                )
                .findFirst()
                .orElse(null);
    }
}
