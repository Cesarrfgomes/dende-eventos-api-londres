package br.com.softhouse.dende.modules.organizadores.repositories;

import br.com.softhouse.dende.exceptions.BadRequestException;
import br.com.softhouse.dende.exceptions.NotFoundException;
import br.com.softhouse.dende.modules.organizadores.model.Organizador;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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

    public Organizador cadastrarOrganizador(Organizador organizador) {
        if (organizador.getId() == null) {
            organizador.setId(this.contadorId);
            this.contadorId++;
        }
        organizador.setIsAtivo(true);
        this.organizadores.put(organizador.getId(), organizador);

        return organizador;
    }

    public void atualizarOrganizador(Long organizadorId, Organizador organizadorAtual, Organizador organizadorAtualizado) {
        this.organizadores.replace(organizadorId, organizadorAtual, organizadorAtualizado);
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
