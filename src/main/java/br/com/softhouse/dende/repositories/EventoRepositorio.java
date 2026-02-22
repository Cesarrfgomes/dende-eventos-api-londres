package br.com.softhouse.dende.repositories;

import br.com.softhouse.dende.exceptions.NotFoundException;
import br.com.softhouse.dende.model.Evento;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EventoRepositorio {

    private static EventoRepositorio instance = new EventoRepositorio();
    private final Map<Long, Evento> eventos;

    private Long contadorId;

    private EventoRepositorio() {
        this.eventos = new HashMap<>();
        this.contadorId = 1L;
    }

    public static EventoRepositorio getInstance() {
        return instance;
    }

    public Evento cadastrarEvento(Evento evento) {
        if (evento.getId() == null) {
            evento.setId(this.contadorId);
            this.contadorId++;
        }

        eventos.put(evento.getId(), evento);

        return  evento;
    }

    public List<Evento> buscarEventosPorOrganizador(Long organizadorId) {
        return this.eventos.values().stream()
                .filter(evento -> evento.getOrganizadorId() == organizadorId)
                .collect(Collectors.toList());
    }

    public List<Evento> buscarFeedEventos() {
        LocalDateTime agora = LocalDateTime.now();
        return this.eventos.values().stream()
                .filter(evento -> Boolean.TRUE.equals(evento.getIsAtivo()))
                .filter(evento -> evento.getDataFim() != null && evento.getDataFim().isAfter(agora))
                .filter(evento -> {
                    int vendidos = (evento.getIngressosVendidos() != null) ? evento.getIngressosVendidos() : 0;
                    int capacidade = (evento.getCapacidadeMaxima() != null) ? evento.getCapacidadeMaxima() : 0;
                    return vendidos < capacidade;
                })

                .sorted(Comparator.comparing(Evento::getDataInicio).thenComparing(Evento::getNome))
                .collect(Collectors.toList());
    }

    public void atualizarEvento(Long eventoId, Evento evento) {
        this.eventos.replace(eventoId, evento);
    }

    public Evento buscarEventoPorId(Long eventoId) {
        var evento =  this.eventos.get(eventoId);

        if (evento == null) {
            throw new NotFoundException("Evento não encontrado");
        }

        return  evento;
    }
}
