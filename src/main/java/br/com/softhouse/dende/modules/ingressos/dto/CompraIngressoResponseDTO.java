package br.com.softhouse.dende.modules.ingressos.dto;

import java.util.List;

public class CompraIngressoResponseDTO {

    private double valorTotal;
    private List<Long> ingressosGerados;

    public CompraIngressoResponseDTO(double valorTotal, List<Long> ingressosGerados) {
        this.valorTotal = valorTotal;
        this.ingressosGerados = ingressosGerados;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public List<Long> getIngressosGerados() {
        return ingressosGerados;
    }

    public void setIngressosGerados(List<Long> ingressosGerados) {
        this.ingressosGerados = ingressosGerados;
    }
}
