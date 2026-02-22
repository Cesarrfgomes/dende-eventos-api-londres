package br.com.softhouse.dende.dto;

import java.util.List;

public class CompraIngressoResponse {

    private double valorTotal;
    private List<Long> ingressosGerados;

    public CompraIngressoResponse(double valorTotal, List<Long> ingressosGerados) {
        this.valorTotal = valorTotal;
        this.ingressosGerados = ingressosGerados;
    }

}