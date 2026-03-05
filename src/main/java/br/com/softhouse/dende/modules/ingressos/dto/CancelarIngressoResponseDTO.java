package br.com.softhouse.dende.modules.ingressos.dto;

public class CancelarIngressoResponseDTO {

    private Long ingressoId;
    private double valorEstornado;
    private String status;

    public CancelarIngressoResponseDTO(Long ingressoId, double valorEstornado, String status) {
        this.ingressoId = ingressoId;
        this.valorEstornado = valorEstornado;
        this.status = status;
    }

}