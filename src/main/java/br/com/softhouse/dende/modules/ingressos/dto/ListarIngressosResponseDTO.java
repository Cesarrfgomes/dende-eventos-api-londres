package br.com.softhouse.dende.modules.ingressos.dto;

import br.com.softhouse.dende.modules.ingressos.model.StatusIngresso;
import java.time.LocalDateTime;

public class ListarIngressosResponseDTO {

    private Long ingressoId;
    private String nomeEvento;
    private LocalDateTime dataInicio;
    private StatusIngresso status;

    public ListarIngressosResponseDTO(Long ingressoId,
                                      String nomeEvento,
                                      LocalDateTime dataInicio,
                                      StatusIngresso status) {
        this.ingressoId = ingressoId;
        this.nomeEvento = nomeEvento;
        this.dataInicio = dataInicio;
        this.status = status;
    }

    public Long getIngressoId() {
        return ingressoId;
    }

    public String getNomeEvento() {
        return nomeEvento;
    }

    public LocalDateTime getDataInicio() {
        return dataInicio;
    }

    public StatusIngresso getStatus() {
        return status;
    }
}