package br.com.softhouse.dende.repositories;

import br.com.softhouse.dende.model.Ingresso;

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
}