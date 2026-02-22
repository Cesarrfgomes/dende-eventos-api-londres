package br.com.softhouse.dende.model;

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

    // getters e setters
}