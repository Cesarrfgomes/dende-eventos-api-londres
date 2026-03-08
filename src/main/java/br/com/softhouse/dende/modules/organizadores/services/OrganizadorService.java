package br.com.softhouse.dende.modules.organizadores.services;

import br.com.softhouse.dende.exceptions.BadRequestException;
import br.com.softhouse.dende.exceptions.NotFoundException;
import br.com.softhouse.dende.modules.organizadores.dto.OrganizadorDTO;
import br.com.softhouse.dende.modules.organizadores.mappers.OrganizadorMapper;
import br.com.softhouse.dende.modules.organizadores.model.Organizador;
import br.com.softhouse.dende.modules.organizadores.repositories.OrganizadorRepositorio;

import java.util.Objects;

public class OrganizadorService {

    private final OrganizadorRepositorio organizadorRepositorio;
    private final OrganizadorMapper organizadorMapper;

    public OrganizadorService() {
        this.organizadorRepositorio = OrganizadorRepositorio.getInstance();
        this.organizadorMapper = new OrganizadorMapper();
    }

    public OrganizadorDTO buscarOrganizadorPorId (long id) {
        var organizador = this.organizadorRepositorio.buscarOrganizadorPorId(id);

        if(organizador == null) {
            throw new NotFoundException("Organizador não encontrado");
        }

        return organizadorMapper.toDto(organizador);
    }

    public OrganizadorDTO buscarOrganizadorPorEmail(String email) {
        var organizador = this.organizadorRepositorio.buscarOrganizadorPorEmail(email);


        return organizadorMapper.toDto(organizador);
    }

    public OrganizadorDTO cadastrarOrganizador(Organizador organizador) {
        OrganizadorDTO organizadorExiste = this.buscarOrganizadorPorEmail(organizador.getEmail());

        if(organizadorExiste != null) {
            throw new BadRequestException("E-mail em uso.");
        }

        if (
                (organizador.getEmpresa().getCnpj() != null && (organizador.getEmpresa().getNomeFantasia() == null || organizador.getEmpresa().getRazaoSocial() == null)) ||
                        (organizador.getEmpresa().getNomeFantasia() != null && (organizador.getEmpresa().getCnpj() == null || organizador.getEmpresa().getRazaoSocial() == null)) ||
                        (organizador.getEmpresa().getRazaoSocial() != null && (organizador.getEmpresa().getNomeFantasia() == null || organizador.getEmpresa().getCnpj() == null))
        ) {
            throw new BadRequestException("Ao informar o CNPj, é necessário informar os campos: Nome Fantasia e Razão Social");
        }

        var novoOrganizador = this.organizadorRepositorio.cadastrarOrganizador(organizador);

        return  organizadorMapper.toDto(novoOrganizador);
    }

    public OrganizadorDTO atualizarOrganizador(Long organizadorId, Organizador organizadorAtualizado){
        OrganizadorDTO organizadorExiste = this.buscarOrganizadorPorId(organizadorId);

        var organizador = this.organizadorMapper.toEntity(organizadorExiste);

        if (!Objects.equals(organizador.getEmail(), organizadorAtualizado.getEmail())) {
            throw new BadRequestException("O e-mail não pode ser alterado");
        }

        organizadorAtualizado.setId(organizadorId);

        this.organizadorRepositorio.atualizarOrganizador(organizadorId, organizador, organizadorAtualizado);

        return this.organizadorMapper.toDto(organizadorAtualizado);
    }

    public void desativarOrganizador(Long organizadorId) {
        OrganizadorDTO organizadorDTO = this.buscarOrganizadorPorId(organizadorId);

        if (!organizadorDTO.isAtivo()) {
            throw new BadRequestException("Oraganizador inativo.");
        }

        var organizador = this.organizadorMapper.toEntity(organizadorDTO);

        organizador.setIsAtivo(false);

        this.atualizarOrganizador(organizadorDTO.id(), organizador);
    }
}
