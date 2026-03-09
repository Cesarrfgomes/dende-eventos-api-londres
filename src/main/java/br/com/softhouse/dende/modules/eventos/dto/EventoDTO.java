package br.com.softhouse.dende.modules.eventos.dto;

import br.com.softhouse.dende.modules.eventos.model.Evento;
import br.com.softhouse.dende.modules.eventos.model.TipoEvento;

import java.time.LocalDateTime;

public record EventoDTO (
    Long id,
    String nome,
    String paginaWeb,
    LocalDateTime dataInicio,
    LocalDateTime dataFim,
    String local,
    TipoEvento tipoEvento,
    long organizadorId,
    Evento eventoPrincipal,
    Double precoUnitarioIngresso,
    Double taxaCancelamento,
    Integer capacidadeMaxima,
    Integer ingressosVendidos,
    Boolean isAtivo,
    Evento eventoVinculado,
    int quantidadeIngressosDisponiveis,
    double valorIngresso
){}
