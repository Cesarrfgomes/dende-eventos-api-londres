package br.com.softhouse.dende.shared.repositories;

import br.com.softhouse.dende.shared.model.EntidadeBase;

import java.util.HashMap;
import java.util.Map;

public abstract class RepositorioBase<T extends EntidadeBase> {
    protected final Map<Long, T> entidades = new HashMap<>();
    private Long contadorId = 1L;

    protected Long proximoId() {
        return this.contadorId++;
    }

    public T buscarPorId(Long id) {
        return entidades.get(id);
    }

    public T salvar(T entidade) {
        if (entidade.getId() == null) {
            entidade.setId(proximoId());
        }
        entidades.put(entidade.getId(), entidade);
        return entidade;
    }
}