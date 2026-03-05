package br.com.softhouse.dende.modules.eventos.dto;

import java.time.LocalDateTime;

public record EventoListagemResponseDTO(
        String nome,
        LocalDateTime dataInicio,
        LocalDateTime dataFim,
        String local,
        Double precoUnitarioIngresso,
        Integer capacidadeMaxima
) {
}
