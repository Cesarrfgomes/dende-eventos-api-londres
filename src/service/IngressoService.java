package br.com.softhouse.dende.service;

import br.com.softhouse.dende.dto.*;
import br.com.softhouse.dende.model.*;
import br.com.softhouse.dende.repositories.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;
import java.util.stream.Collectors;

public class IngressoService {

    private IngressoRepositorio ingressoRepositorio = new IngressoRepositorio();
    private EventoRepositorio eventoRepositorio = new EventoRepositorio();
    private UsuarioRepositorio usuarioRepositorio = new UsuarioRepositorio();

    public CompraIngressoResponse comprar(CompraIngressoRequest request) {

        Evento evento = eventoRepositorio.buscarPorId(request.getEventoId());
        Usuario usuario = usuarioRepositorio.buscarPorId(request.getUsuarioId());

        if (evento == null || usuario == null) {
            throw new RuntimeException("Usuário ou evento não encontrado.");
        }

        if (evento.getQuantidadeIngressosDisponiveis() <= 0) {
            throw new RuntimeException("Evento lotado.");
        }

        double valorTotal = 0;
        List<Long> ingressosGerados = new ArrayList<>();


        Ingresso ingressoPrincipal = new Ingresso();
        ingressoPrincipal.setUsuario(usuario);
        ingressoPrincipal.setEvento(evento);
        ingressoPrincipal.setValorPago(evento.getValorIngresso());
        ingressoPrincipal.setStatus(StatusIngresso.ATIVO);
        ingressoPrincipal.setDataCompra(LocalDateTime.now());

        ingressoRepositorio.salvar(ingressoPrincipal);

        evento.setQuantidadeIngressosDisponiveis(
                evento.getQuantidadeIngressosDisponiveis() - 1
        );

        valorTotal += evento.getValorIngresso();
        ingressosGerados.add(ingressoPrincipal.getId());

    
        if (evento.getEventoVinculado() != null) {

            Evento vinculado = evento.getEventoVinculado();

            Ingresso ingressoVinculado = new Ingresso();
            ingressoVinculado.setUsuario(usuario);
            ingressoVinculado.setEvento(vinculado);
            ingressoVinculado.setValorPago(vinculado.getValorIngresso());
            ingressoVinculado.setStatus(StatusIngresso.ATIVO);
            ingressoVinculado.setDataCompra(LocalDateTime.now());

            ingressoRepositorio.salvar(ingressoVinculado);

            valorTotal += vinculado.getValorIngresso();
            ingressosGerados.add(ingressoVinculado.getId());
        }

        return new CompraIngressoResponse(valorTotal, ingressosGerados);
    }

    public CancelarIngressoResponse cancelar(CancelarIngressoRequest request) {

        Ingresso ingresso = ingressoRepositorio.buscarPorId(request.getIngressoId());

        if (ingresso == null) {
            throw new RuntimeException("Ingresso não encontrado.");
        }

        if (!ingresso.isAtivo()) {
            throw new RuntimeException("Ingresso já cancelado.");
        }

        Evento evento = ingresso.getEvento();


        ingresso.cancelar();


        evento.setQuantidadeIngressosDisponiveis(
                evento.getQuantidadeIngressosDisponiveis() + 1
        );


        double valorEstorno = ingresso.getValorPago();

        return new CancelarIngressoResponse(
                ingresso.getId(),
                valorEstorno,
                "CANCELADO"
        );
    }


    public List<ListarIngressosResponse> listarIngressosPorUsuario(Long usuarioId) {

        List<Ingresso> ingressos = ingressoRepository.buscarPorUsuarioId(usuarioId);

        return ingressos.stream()
                .sorted(Comparator
                        .comparing((Ingresso i) -> prioridade(i))
                        .thenComparing(i -> i.getEvento().getDataInicio())
                        .thenComparing(i -> i.getEvento().getNome())
                )
                .map(i -> new ListarIngressosResponse(
                        i.getId(),
                        i.getEvento().getNome(),
                        i.getEvento().getDataInicio(),
                        i.getStatus()
                ))
                .collect(Collectors.toList());
    }

    private int prioridade(Ingresso ingresso) {

        boolean eventoAtivo = ingresso.getEvento().getIsAtivo();
        boolean eventoFuturo = ingresso.getEvento()
                .getDataInicio()
                .isAfter(LocalDateTime.now());

        boolean ingressoCancelado = ingresso.getStatus() == StatusIngresso.CANCELADO;

        // Prioridade 0 → aparece primeiro
        if (eventoAtivo && eventoFuturo && !ingressoCancelado) {
            return 0;
        }

        // Prioridade 1 → aparece depois
        return 1;
    }
}

