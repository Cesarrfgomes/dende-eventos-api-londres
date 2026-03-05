package br.com.softhouse.dende.controller;

import br.com.softhouse.dende.modules.ingressos.dto.*;
import br.com.softhouse.dende.modules.ingressos.services.IngressoService;

import java.util.List;

public class IngressoController {

    private IngressoService ingressoService = new IngressoService();


    public CompraIngressoResponseDTO comprarIngresso(CompraIngressoRequestDTO request) {
        return ingressoService.comprar(request);
    }


    public CancelarIngressoResponseDTO cancelarIngresso(CancelarIngressoRequestDTO request) {
        return ingressoService.cancelar(request);
    }

    public List<ListarIngressosResponseDTO> listarPorUsuario(Long usuarioId) {
        return ingressoService.listarIngressosPorUsuario(usuarioId);
    }
}
