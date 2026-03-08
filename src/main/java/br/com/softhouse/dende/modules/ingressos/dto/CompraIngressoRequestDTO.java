package br.com.softhouse.dende.modules.ingressos.dto;

public class CompraIngressoRequestDTO {

    private Long usuarioId;
    private Long eventoId;

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Long getEventoId() {
        return eventoId;
    }

    public void setEventoId(Long eventoId) {
        this.eventoId = eventoId;
    }
}
