package br.com.softhouse.dende.dto;

import br.com.softhouse.dende.model.Evento;
import br.com.softhouse.dende.model.TipoEvento;

import java.time.LocalDateTime;

public record AtualizarEventoRequestDTO(
        String nome,
        String paginaWeb,
        LocalDateTime dataInicio,
        LocalDateTime dataFim,
        TipoEvento tipoEvento,
        Evento eventoPrincipal,
        Double precoUnitarioIngresso,
        Double taxaCancelamento,
        Integer capacidadeMaxima
) {
}
