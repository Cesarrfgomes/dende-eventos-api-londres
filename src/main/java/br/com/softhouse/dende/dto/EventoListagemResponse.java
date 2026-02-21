package br.com.softhouse.dende.dto;

import br.com.softhouse.dende.model.Evento;
import java.time.LocalDateTime;

public class EventoListagemResponse {

    private String nome;
    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;
    private String local;
    private Double precoUnitarioIngresso;
    private Integer capacidadeMaxima;

    public EventoListagemResponse(Evento evento) {
        this.nome = evento.getNome();
        this.dataInicio = evento.getDataInicio();
        this.dataFim = evento.getDataFim();
         this.local = evento.getLocal();
        this.precoUnitarioIngresso = evento.getPrecoUnitarioIngresso();
        this.capacidadeMaxima = evento.getCapacidadeMaxima();
    }

    public String getNome() { return nome; }
    public LocalDateTime getDataInicio() { return dataInicio; }
    public LocalDateTime getDataFim() { return dataFim; }
    public String getLocal() { return local; }
    public Double getPrecoUnitarioIngresso() { return precoUnitarioIngresso; }
    public Integer getCapacidadeMaxima() { return capacidadeMaxima; }
}
