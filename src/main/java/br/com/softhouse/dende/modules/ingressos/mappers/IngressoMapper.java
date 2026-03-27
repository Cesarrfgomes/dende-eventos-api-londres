package br.com.softhouse.dende.modules.ingressos.mappers;

import br.com.softhouse.dende.modules.eventos.mappers.EventoMapper;
import br.com.softhouse.dende.modules.ingressos.dto.IngressoDTO;
import br.com.softhouse.dende.modules.ingressos.model.Ingresso;
import br.com.softhouse.dende.modules.usuarios.mappers.UsuarioMapper;

public class IngressoMapper {

    private final UsuarioMapper usuarioMapper;
    private final EventoMapper eventoMapper;

    public IngressoMapper() {
        this.usuarioMapper = new UsuarioMapper();
        this.eventoMapper = new EventoMapper();
    }

    public IngressoDTO toDto(Ingresso ingresso) {
        if (ingresso == null) {
            return null;
        }

        return new IngressoDTO(
                ingresso.getId(),
                usuarioMapper.toDto(ingresso.getUsuario()),
                eventoMapper.toDto(ingresso.getEvento()),
                ingresso.getValorPago(),
                ingresso.getStatus(),
                ingresso.getDataCompra(),
                ingresso.getDataCancelamento()
        );
    }

    public Ingresso toEntity(IngressoDTO dto) {
        if (dto == null) {
            return null;
        }

        Ingresso ingresso = new Ingresso();
        ingresso.setId(dto.id());
        ingresso.setUsuario(usuarioMapper.toEntity(dto.usuario()));
        ingresso.setEvento(eventoMapper.toEntity(dto.evento()));
        ingresso.setValorPago(dto.valorPago());
        ingresso.setStatus(dto.status());
        ingresso.setDataCompra(dto.dataCompra());
        ingresso.setDataCancelamento(dto.dataCancelamento());

        return ingresso;
    }
}
