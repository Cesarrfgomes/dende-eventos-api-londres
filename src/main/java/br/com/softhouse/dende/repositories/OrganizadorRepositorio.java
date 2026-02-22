package br.com.softhouse.dende.repositories;

import br.com.softhouse.dende.exceptions.BadRequestException;
import br.com.softhouse.dende.exceptions.NotFoundException;
import br.com.softhouse.dende.model.Organizador;

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

    public Organizador buscarOrganizadorPorId(Long id) throws NotFoundException {
        var organizador = organizadores.get(id);

        if (organizador == null) {
            throw new NotFoundException("organizador nao encontrado");
        }

        return organizador;
    }

    public Organizador cadastrarOrganizador(Organizador organizador) {
        var organizadorExiste = this.buscarOrganizadorPorEmail(organizador.getEmail());

        if (organizadorExiste != null) {
            throw new BadRequestException("E-mail em uso por outro usuário");
        }

        if (
                (organizador.getEmpresa().getCnpj() != null && (organizador.getEmpresa().getNomeFantasia() == null || organizador.getEmpresa().getRazaoSocial() == null)) ||
                        (organizador.getEmpresa().getNomeFantasia() != null && (organizador.getEmpresa().getCnpj() == null || organizador.getEmpresa().getRazaoSocial() == null)) ||
                        (organizador.getEmpresa().getRazaoSocial() != null && (organizador.getEmpresa().getNomeFantasia() == null || organizador.getEmpresa().getCnpj() == null))
        ) {
            throw new BadRequestException("Ao informar o CNPj, é necessário informar os campos: Nome Fantasia e Razão Social");
        }

        if (organizador.getId() == null) {
            organizador.setId(this.contadorId);
            this.contadorId++;
        }
        organizador.setIsAtivo(true);
        this.organizadores.put(organizador.getId(), organizador);

        return organizador;
    }

    public Organizador atualizarOrganizador(Long organizadorId, Organizador organizadorAtualizado) {
        var organizador = this.buscarOrganizadorPorId(organizadorId);

        if (!Objects.equals(organizador.getEmail(), organizadorAtualizado.getEmail())) {
            throw new BadRequestException("O e-mail não pode ser alterado");
        }

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
