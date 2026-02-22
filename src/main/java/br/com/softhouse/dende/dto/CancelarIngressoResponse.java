package br.com.softhouse.dende.dto;

public class CancelarIngressoResponse {

    private Long ingressoId;
    private double valorEstornado;
    private String status;

    public CancelarIngressoResponse(Long ingressoId, double valorEstornado, String status) {
        this.ingressoId = ingressoId;
        this.valorEstornado = valorEstornado;
        this.status = status;
    }

}