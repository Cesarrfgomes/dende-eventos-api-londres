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

    public Long getIngressoId() {
        return ingressoId;
    }

    public void setIngressoId(Long ingressoId) {
        this.ingressoId = ingressoId;
    }

    public double getValorEstornado() {
        return valorEstornado;
    }

    public void setValorEstornado(double valorEstornado) {
        this.valorEstornado = valorEstornado;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
