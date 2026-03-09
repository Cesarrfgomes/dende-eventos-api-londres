package br.com.softhouse.dende.modules.ingressos.dto;

import br.com.softhouse.dende.modules.eventos.model.Evento;
import br.com.softhouse.dende.modules.ingressos.model.StatusIngresso;
import br.com.softhouse.dende.modules.usuarios.model.Usuario;

import java.time.LocalDateTime;

public record IngressoDTO (
        Long id,
        Usuario usuario,
        Evento evento,
        double valorPago,
        StatusIngresso status,
        LocalDateTime dataCompra,
        LocalDateTime dataCancelamento
){ }
