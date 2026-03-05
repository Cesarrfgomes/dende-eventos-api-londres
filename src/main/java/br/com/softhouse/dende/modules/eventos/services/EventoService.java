package br.com.softhouse.dende.modules.eventos.services;

import br.com.softhouse.dende.modules.eventos.dto.CriarEventoRequestDTO;
import br.com.softhouse.dende.modules.eventos.model.Evento;
import br.com.softhouse.dende.modules.eventos.repositories.EventoRepositorio;
import br.com.softhouse.dende.modules.organizadores.model.Organizador;
import br.com.softhouse.dende.modules.organizadores.repositories.OrganizadorRepositorio;

public class EventoService {

    private final OrganizadorRepositorio organizadorRepositorio;
    private final EventoRepositorio eventoRepositorio;

    public EventoService() {
        this.organizadorRepositorio = OrganizadorRepositorio.getInstance();
        this.eventoRepositorio = EventoRepositorio.getInstance();
    }

    public Evento criarEvento(long organizadorId, CriarEventoRequestDTO dto) {

        Organizador organizador = this.organizadorRepositorio.buscarOrganizadorPorId(organizadorId);

        Evento evento = Evento.builder()
                .nome(dto.nome())
                .paginaWeb(dto.paginaWeb())
                .dataInicio(dto.dataInicio())
                .dataFim(dto.dataFim())
                .local(dto.local())
                .tipoEvento(dto.tipoEvento())
                .organizadorId(organizadorId)
                .precoUnitarioIngresso(dto.precoUnitarioIngresso())
                .taxaCancelamento(dto.taxaCancelamento())
                .capacidadeMaxima(dto.capacidadeMaxima())
                .build();

        return this.eventoRepositorio.cadastrarEvento(evento);
    }

}
