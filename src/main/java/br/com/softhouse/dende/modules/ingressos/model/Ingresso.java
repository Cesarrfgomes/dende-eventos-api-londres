package br.com.softhouse.dende.modules.ingressos.model;

import br.com.softhouse.dende.modules.eventos.model.Evento;
import br.com.softhouse.dende.modules.usuarios.model.Usuario;

import java.time.LocalDateTime;

public class Ingresso {

    private static Long contador = 1L;

    private Long id;
    private Usuario usuario;
    private Evento evento;
    private double valorPago;
    private StatusIngresso status;
    private LocalDateTime dataCompra;
    private LocalDateTime dataCancelamento;

    public Ingresso() {
        this.id = contador++;
    }

    public void cancelar() {
        this.status = StatusIngresso.CANCELADO;
        this.dataCancelamento = LocalDateTime.now();
    }

    public boolean isAtivo() {
        return this.status == StatusIngresso.ATIVO;
    }

    // --- Getters e Setters ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public double getValorPago() {
        return valorPago;
    }

    public void setValorPago(double valorPago) {
        this.valorPago = valorPago;
    }

    public StatusIngresso getStatus() {
        return status;
    }

    public void setStatus(StatusIngresso status) {
        this.status = status;
    }

    public LocalDateTime getDataCompra() {
        return dataCompra;
    }

    public void setDataCompra(LocalDateTime dataCompra) {
        this.dataCompra = dataCompra;
    }

    public LocalDateTime getDataCancelamento() {
        return dataCancelamento;
    }

    public void setDataCancelamento(LocalDateTime dataCancelamento) {
        this.dataCancelamento = dataCancelamento;
    }
}