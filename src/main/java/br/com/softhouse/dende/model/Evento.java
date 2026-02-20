package br.com.softhouse.dende.model;

import java.time.LocalDateTime;

public class Evento {
    private Long id;
    private String nome;
    private String paginaWeb;
    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;
    private TipoEvento tipoEvento;
    private Evento eventoPrincipal;
    private Double precoUnitarioIngresso;
    private Double taxaCancelamento;
    private Integer capacidadeMaxima;

    public Evento() {

    }

    public Evento(String nome,
                  String paginaWeb,
                  LocalDateTime dataInicio,
                  LocalDateTime dataFim,
                  TipoEvento tipoEvento,
                  Evento eventoPrincipal,
                  Double precoUnitarioIngresso,
                  Double taxaCancelamento,
                  Integer capacidadeMaxima) {

        this.nome = nome;
        this.paginaWeb = paginaWeb;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.tipoEvento = tipoEvento;
        this.eventoPrincipal = eventoPrincipal;
        this.precoUnitarioIngresso = precoUnitarioIngresso;
        this.taxaCancelamento = taxaCancelamento;
        this.capacidadeMaxima = capacidadeMaxima;

    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPaginaWeb() {
        return paginaWeb;
    }

    public void setPaginaWeb(String paginaWeb) {
        this.paginaWeb = paginaWeb;
    }

    public LocalDateTime getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDateTime dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDateTime getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDateTime dataFim) {
        this.dataFim = dataFim;
    }

    public TipoEvento getTipoEvento() {
        return tipoEvento;
    }

    public void setTipoEvento(TipoEvento tipoEvento) {
        this.tipoEvento = tipoEvento;
    }

    public Evento getEventoPrincipal() {
        return eventoPrincipal;
    }

    public void setEventoPrincipal(Evento eventoPrincipal) {
        this.eventoPrincipal = eventoPrincipal;
    }

    public Double getPrecoUnitarioIngresso() {
        return precoUnitarioIngresso;
    }

    public void setPrecoUnitarioIngresso(Double precoUnitarioIngresso) {
        this.precoUnitarioIngresso = precoUnitarioIngresso;
    }

    public Double getTaxaCancelamento() {
        return taxaCancelamento;
    }

    public void setTaxaCancelamento(Double taxaCancelamento) {
        this.taxaCancelamento = taxaCancelamento;
    }

    public Integer getCapacidadeMaxima() {
        return capacidadeMaxima;
    }

    public void setCapacidadeMaxima(Integer capacidadeMaxima) {
        this.capacidadeMaxima = capacidadeMaxima;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }


}
