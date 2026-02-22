package br.com.softhouse.dende.dto;

import br.com.softhouse.dende.model.Empresa;
import br.com.softhouse.dende.model.Organizador;

import java.time.LocalDate;
import java.time.Period;

public record OrganizadorPerfilResponseDTO(
        String nome,
        String email,
         LocalDate dataNascimento,
         String idade,
         String sexo,
         boolean isAtivo,
         Empresa empresa
) {

    public OrganizadorPerfilResponseDTO(Organizador organizador) {
        this(
                organizador.getNome(),
                organizador.getEmail(),
                organizador.getDataNascimento(),
                calcularIdade(organizador.getDataNascimento()),
                organizador.getSexo(),
                organizador.getIsAtivo(),
                organizador.getEmpresa()
        );
    }

    private static String calcularIdade(LocalDate nascimento) {
        if (nascimento == null) {
            return null;
        }

        Period periodo = Period.between(nascimento, LocalDate.now());

        return periodo.getYears() + " anos, "
                + periodo.getMonths() + " meses e "
                + periodo.getDays() + " dias";
    }
}