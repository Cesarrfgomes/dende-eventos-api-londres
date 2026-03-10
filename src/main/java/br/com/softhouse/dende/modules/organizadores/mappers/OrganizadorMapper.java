package br.com.softhouse.dende.modules.organizadores.mappers;

import br.com.softhouse.dende.modules.organizadores.dto.OrganizadorDTO;
import br.com.softhouse.dende.modules.organizadores.model.Organizador;

import java.time.LocalDate;
import java.time.Period;

public class OrganizadorMapper {

    public OrganizadorDTO toDto(Organizador organizador) {
        if (organizador == null) {
            return null;
        }

        boolean ativo = organizador.getIsAtivo() != null ? organizador.getIsAtivo() : false;

        return new OrganizadorDTO(
                organizador.getId(),
                organizador.getNome(),
                organizador.getEmail(),
                organizador.getDataNascimento(),
                this.calcularIdade(organizador.getDataNascimento()),
                organizador.getSexo(),
                ativo,
                organizador.getEmpresa()
        );
    }

    public Organizador toEntity(OrganizadorDTO dto) {
        if (dto == null) {
            return null;
        }

        Organizador organizador = new Organizador();

        organizador.setNome(dto.nome());
        organizador.setDataNascimento(dto.dataNascimento());
        organizador.setSexo(dto.sexo());
        organizador.setEmail(dto.email());
        organizador.setempresa(dto.empresa());

        return organizador;
    }
    private String calcularIdade(LocalDate nascimento) {
        if (nascimento == null) {
            return null;
        }

        Period periodo = Period.between(nascimento, LocalDate.now());

        return periodo.getYears() + " anos, "
                + periodo.getMonths() + " meses e "
                + periodo.getDays() + " dias";
    }


}
