package br.com.softhouse.dende.modules.ingressos.repositories;

import br.com.softhouse.dende.modules.ingressos.model.Ingresso;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class IngressoRepositorio {

    private List<Ingresso> ingressos = new ArrayList<>();

    public void salvar(Ingresso ingresso) {
        ingressos.add(ingresso);
    }

    public List<Ingresso> listarTodos() {
        return ingressos;
    }

    public Ingresso buscarPorId(Long id) {
        return ingressos.stream()
                .filter(i -> i.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public List<Ingresso> buscarPorUsuarioId(Long usuarioId) {
        return ingressos.stream()
                .filter(i -> i.getUsuario().getId().equals(usuarioId))
                .collect(Collectors.toList());
    }
}

