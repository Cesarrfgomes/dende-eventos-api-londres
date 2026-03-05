package br.com.softhouse.dende.modules.eventos.dto;

import br.com.softhouse.dende.modules.eventos.model.TipoEvento;

import java.time.LocalDateTime;

public record CriarEventoRequestDTO(
        String nome,
        String paginaWeb,
        LocalDateTime dataInicio,
        LocalDateTime dataFim,
        String local,
        TipoEvento tipoEvento,
        Double precoUnitarioIngresso,
        Double taxaCancelamento,
        Integer capacidadeMaxima
) { }
