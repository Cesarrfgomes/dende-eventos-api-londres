package br.com.softhouse.dende.modules.ingressos.dto;

import java.util.List;

public class CompraIngressoResponseDTO {

    private double valorTotal;
    private List<Long> ingressosGerados;

    public CompraIngressoResponseDTO(double valorTotal, List<Long> ingressosGerados) {
        this.valorTotal = valorTotal;
        this.ingressosGerados = ingressosGerados;
    }

}