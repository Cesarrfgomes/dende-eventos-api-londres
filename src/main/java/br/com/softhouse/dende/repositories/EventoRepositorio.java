package br.com.softhouse.dende.repositories;

import br.com.softhouse.dende.dto.AtualizarEventoRequest;
import br.com.softhouse.dende.model.Evento;
import br.com.softhouse.dende.model.Organizador;

import java.util.HashMap;
import java.util.Map;

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

    public void atualizarEvento(Long eventoId, Evento evento) {
        this.eventos.replace(eventoId, evento);
    }

    public Evento buscarEventoPorId(Long eventoId) {
        return this.eventos.get(eventoId);
    }
}
