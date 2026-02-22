package br.com.softhouse.dende.controller;

import br.com.softhouse.dende.dto.CompraIngressoRequest;
import br.com.softhouse.dende.dto.CompraIngressoResponse;
import br.com.softhouse.dende.dto.CancelarIngressoRequest;
import br.com.softhouse.dende.dto.CancelarIngressoResponse;
import br.com.softhouse.dende.service.IngressoService;

public class IngressoController {

    private IngressoService ingressoService = new IngressoService();


    public CompraIngressoResponse comprarIngresso(CompraIngressoRequest request) {
        return ingressoService.comprar(request);
    }


    public CancelarIngressoResponse cancelarIngresso(CancelarIngressoRequest request) {
        return ingressoService.cancelar(request);
    }
}
public List<ListarIngressosResponse> listarPorUsuario(Long usuarioId) {
    return ingressoService.listarIngressosPorUsuario(usuarioId);
}