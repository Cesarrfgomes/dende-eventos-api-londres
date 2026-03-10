package br.com.softhouse.dende.modules.eventos.dto;

import java.time.LocalDateTime;

import br.com.softhouse.dende.modules.eventos.model.TipoEvento;

public record EventoDTO(
    Long id,
    String nome,
    String paginaWeb,
    LocalDateTime dataInicio,
    LocalDateTime dataFim,
    String local,
    TipoEvento tipoEvento,
    Double precoUnitarioIngresso,
    Double taxaCancelamento,
    Integer capacidadeMaxima
) {
}
