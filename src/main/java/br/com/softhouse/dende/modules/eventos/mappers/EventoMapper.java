package br.com.softhouse.dende.modules.eventos.mappers;

import br.com.softhouse.dende.modules.eventos.dto.EventoDTO;
import br.com.softhouse.dende.modules.eventos.model.Evento;

public class EventoMapper {

    public EventoDTO toDto(Evento evento) {
        if (evento == null) {
            return null;
        }

        return new EventoDTO(
                evento.getId(),
                evento.getNome(),
                evento.getPaginaWeb(),
                evento.getDataInicio(),
                evento.getDataFim(),
                evento.getLocal(),
                evento.getTipoEvento(),
                evento.getPrecoUnitarioIngresso(),
                evento.getTaxaCancelamento(),
                evento.getCapacidadeMaxima()
        );
    }

    public Evento toEntity(EventoDTO dto) {
        if (dto == null) {
            return null;
        }

        Evento evento = new Evento();
        evento.setId(dto.id());
        evento.setNome(dto.nome());
        evento.setPaginaWeb(dto.paginaWeb());
        evento.setDataInicio(dto.dataInicio());
        evento.setDataFim(dto.dataFim());
        evento.setLocal(dto.local());
        evento.setTipoEvento(dto.tipoEvento());
        evento.setPrecoUnitarioIngresso(dto.precoUnitarioIngresso());
        evento.setTaxaCancelamento(dto.taxaCancelamento());
        evento.setCapacidadeMaxima(dto.capacidadeMaxima());

        return evento;
    }
}
