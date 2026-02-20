package br.com.softhouse.dende.dto;

import br.com.softhouse.dende.model.Usuario;

import java.time.LocalDate;
import java.time.Period;

public class UsuarioPerfilResponse {

    private final String nome;
    private final String email;
    private final LocalDate dataNascimento;
    private final String idade;

    public UsuarioPerfilResponse(Usuario usuario) {
        this.nome = usuario.getNome();
        this.email = usuario.getEmail();
        this.dataNascimento = usuario.getDataNascimento();
        this.idade = calcularIdade(usuario.getDataNascimento());
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

    public String getNome() { return nome; }

    public String getEmail() { return email; }

    public LocalDate getDataNascimento() { return dataNascimento; }

    public String getIdade() { return idade; }
}