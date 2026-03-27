package br.com.softhouse.dende.modules.ingressos.dto;

import java.time.LocalDateTime;

import br.com.softhouse.dende.modules.eventos.dto.EventoDTO;
import br.com.softhouse.dende.modules.ingressos.model.StatusIngresso;
import br.com.softhouse.dende.modules.usuarios.dto.UsuarioDTO;

public record IngressoDTO(
    Long id,
    UsuarioDTO usuario,
    EventoDTO evento,
    double valorPago,
    StatusIngresso status,
    LocalDateTime dataCompra,
    LocalDateTime dataCancelamento
) {

}
