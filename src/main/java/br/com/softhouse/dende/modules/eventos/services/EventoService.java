package br.com.softhouse.dende.modules.eventos.services;

import br.com.softhouse.dende.exceptions.NotFoundException;
import br.com.softhouse.dende.modules.eventos.dto.CriarEventoRequestDTO;
import br.com.softhouse.dende.modules.eventos.model.Evento;
import br.com.softhouse.dende.modules.eventos.repositories.EventoRepositorio;
import br.com.softhouse.dende.modules.organizadores.dto.OrganizadorDTO;
import br.com.softhouse.dende.modules.organizadores.services.OrganizadorService;

import java.util.List;

public class EventoService {

    private final EventoRepositorio eventoRepositorio;
    private final OrganizadorService organizadorService;

    public EventoService() {
        this.eventoRepositorio = EventoRepositorio.getInstance();
        this.organizadorService = new OrganizadorService();
    }

    public Evento buscarEventoPorId(Long id) {
        var evento = this.eventoRepositorio.buscarEventoPorId(id);

        if (evento == null) {
            throw new NotFoundException("Evento não encontrado");
        }

        return evento;
    }

    public List<Evento> buscarEventosPorOrganizadorId(Long organizadorId) {
        return this.eventoRepositorio.buscarEventosPorOrganizador(organizadorId);
    }

    public Evento criarEvento(long organizadorId, CriarEventoRequestDTO dto) {

        OrganizadorDTO organizadorDTO = this.organizadorService.buscarOrganizadorPorId(organizadorId);

        Evento evento = Evento.builder()
                .nome(dto.nome())
                .paginaWeb(dto.paginaWeb())
                .dataInicio(dto.dataInicio())
                .dataFim(dto.dataFim())
                .local(dto.local())
                .tipoEvento(dto.tipoEvento())
                .organizadorId(organizadorDTO.id())
                .precoUnitarioIngresso(dto.precoUnitarioIngresso())
                .taxaCancelamento(dto.taxaCancelamento())
                .capacidadeMaxima(dto.capacidadeMaxima())
                .build();

        return this.eventoRepositorio.cadastrarEvento(evento);
    }

    public Evento ativarEvento(long organizadorId, long eventoId) {

        Evento evento = this.buscarEventoPorId(eventoId);

        if (evento.getOrganizadorId() != organizadorId) {
            throw new RuntimeException("Usuário sem permissão para alterar este evento.");
        }

        evento.setIsAtivo(true);

        this.eventoRepositorio.atualizarEvento(eventoId, evento);

        return evento;
    }

    public Evento cancelarEvento(long organizadorId, long eventoId) {

        Evento evento = this.buscarEventoPorId(eventoId);

        if (evento.getOrganizadorId() != organizadorId) {
            throw new RuntimeException("Usuário sem permissão para alterar este evento.");
        }

        evento.setIsAtivo(false);

        this.eventoRepositorio.atualizarEvento(eventoId, evento);

        return evento;
    }

}
