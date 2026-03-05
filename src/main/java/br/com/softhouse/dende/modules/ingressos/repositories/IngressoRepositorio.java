package br.com.softhouse.dende.modules.ingressos.repositories;

import br.com.softhouse.dende.modules.ingressos.model.Ingresso;

import java.util.ArrayList;
import java.util.List;

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
}

