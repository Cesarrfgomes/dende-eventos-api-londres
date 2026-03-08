package br.com.softhouse.dende.modules.eventos.services;

import br.com.dende.softhouse.process.route.ResponseEntity;
import br.com.softhouse.dende.exceptions.ErroDTO;
import br.com.softhouse.dende.exceptions.NotFoundException;
import br.com.softhouse.dende.modules.eventos.dto.AtualizarEventoRequestDTO;
import br.com.softhouse.dende.modules.eventos.dto.CriarEventoRequestDTO;
import br.com.softhouse.dende.modules.eventos.model.Evento;
import br.com.softhouse.dende.modules.eventos.repositories.EventoRepositorio;
import br.com.softhouse.dende.modules.organizadores.dto.OrganizadorDTO;
import br.com.softhouse.dende.modules.organizadores.services.OrganizadorService;
import br.com.softhouse.dende.modules.usuarios.dto.AtualizarUsuarioRequestDTO;
import br.com.softhouse.dende.modules.usuarios.model.Usuario;

import java.util.List;
import java.util.Objects;

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

    public Evento atualizarEvento(long eventoId, long organizadorId, AtualizarEventoRequestDTO dto) {
        Evento eventoExiste = this.buscarEventoPorId(eventoId);

        if (eventoExiste.getOrganizadorId() != organizadorId) {
            throw new SecurityException("Usuário sem permissão para alterar o evento.");
        }

        eventoExiste.setNome(dto.nome());
        eventoExiste.setPaginaWeb(dto.paginaWeb());
        eventoExiste.setDataInicio(dto.dataInicio());
        eventoExiste.setDataFim(dto.dataFim());
        eventoExiste.setTipoEvento(dto.tipoEvento());
        eventoExiste.setEventoPrincipal(dto.eventoPrincipal());
        eventoExiste.setPrecoUnitarioIngresso(dto.precoUnitarioIngresso());
        eventoExiste.setTaxaCancelamento(dto.taxaCancelamento());
        eventoExiste.setCapacidadeMaxima(dto.capacidadeMaxima());

        return this.eventoRepositorio.atualizarEvento(eventoId, eventoExiste);
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
