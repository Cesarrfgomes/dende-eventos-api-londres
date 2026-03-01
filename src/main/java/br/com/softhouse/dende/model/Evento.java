package br.com.softhouse.dende.model;

import java.time.LocalDateTime;

public class Evento {
    private Long id;
    private String nome;
    private String paginaWeb;
    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;
    private String local;
    private TipoEvento tipoEvento;
    private long organizadorId;
    private Evento eventoPrincipal;
    private Double precoUnitarioIngresso;
    private Double taxaCancelamento;
    private Integer capacidadeMaxima;
    private Integer ingressosVendidos = 0;
    private Boolean isAtivo;
    private Evento eventoVinculado;
    private int quantidadeIngressosDisponiveis;
    private double valorIngresso;

    public Evento() {
        this.isAtivo = true;
        this.ingressosVendidos = 0;
    }

   public static Builder builder(){
        return new Builder();
   }

   public static class Builder {
        private final Evento evento;

        private Builder() {
            this.evento = new Evento();
        }

        public Builder id(Long id) {
            evento.id = id;
            return this;
        }

       public Builder nome(String nome) {
           evento.nome = nome;
           return this;
       }

       public Builder paginaWeb(String paginaWeb) {
           evento.paginaWeb = paginaWeb;
           return this;
       }

       public Builder dataInicio(LocalDateTime dataInicio) {
           evento.dataInicio = dataInicio;
           return this;
       }

       public Builder dataFim(LocalDateTime dataFim) {
           evento.dataFim = dataFim;
           return this;
       }

       public Builder local(String local) {
           evento.local = local;
           return this;
       }

       public Builder tipoEvento(TipoEvento tipoEvento) {
           evento.tipoEvento = tipoEvento;
           return this;
       }

       public Builder organizadorId(long organizadorId) {
           evento.organizadorId = organizadorId;
           return this;
       }

       public Builder eventoPrincipal(Evento eventoPrincipal) {
           evento.eventoPrincipal = eventoPrincipal;
           return this;
       }

       public Builder precoUnitarioIngresso(Double precoUnitarioIngresso) {
           evento.precoUnitarioIngresso = precoUnitarioIngresso;
           return this;
       }

       public Builder taxaCancelamento(Double taxaCancelamento) {
           evento.taxaCancelamento = taxaCancelamento;
           return this;
       }

       public Builder capacidadeMaxima(Integer capacidadeMaxima) {
           evento.capacidadeMaxima = capacidadeMaxima;
           return this;
       }

       public Builder ingressosVendidos(Integer ingressosVendidos) {
           evento.ingressosVendidos = ingressosVendidos;
           return this;
       }

       public Builder isAtivo(Boolean isAtivo) {
           evento.isAtivo = isAtivo;
           return this;
       }

       public Builder eventoVinculado(Evento eventoVinculado) {
           evento.eventoVinculado = eventoVinculado;
           return this;
       }

       public Builder quantidadeIngressosDisponiveis(int quantidadeIngressosDisponiveis) {
           evento.quantidadeIngressosDisponiveis = quantidadeIngressosDisponiveis;
           return this;
       }

       public Builder valorIngresso(double valorIngresso) {
           evento.valorIngresso = valorIngresso;
           return this;
       }

       public Evento build() {
           return evento;
       }
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

    public String getLocal() { return local; }

    public void setLocal(String local) { this.local = local; }

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

    public Integer getIngressosVendidos() { return ingressosVendidos; }

    public void setIngressosVendidos(Integer ingressosVendidos) { this.ingressosVendidos = ingressosVendidos; }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getIsAtivo() {
        return this.isAtivo;
    }

    public void setIsAtivo(Boolean isAtivo) {
        this.isAtivo = isAtivo;
    }

    public long getOrganizadorId() {
        return organizadorId;
    }

    public void setOrganizadorId(long organizadorId) {
        this.organizadorId = organizadorId;
    }
}
