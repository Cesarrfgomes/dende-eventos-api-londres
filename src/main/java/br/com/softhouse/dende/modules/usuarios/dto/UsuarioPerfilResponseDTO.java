package br.com.softhouse.dende.modules.usuarios.dto;

import br.com.softhouse.dende.modules.usuarios.model.Usuario;

import java.time.LocalDate;
import java.time.Period;

public record UsuarioPerfilResponseDTO(
        String nome,
        String email,
        LocalDate dataNascimento,
        String idade
) {

    public UsuarioPerfilResponseDTO(Usuario usuario) {
        this(
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getDataNascimento(),
                calcularIdade(usuario.getDataNascimento())
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