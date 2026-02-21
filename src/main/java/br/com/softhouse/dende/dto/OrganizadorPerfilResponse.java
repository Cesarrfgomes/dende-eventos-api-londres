package br.com.softhouse.dende.dto;

import br.com.softhouse.dende.model.Empresa;
import br.com.softhouse.dende.model.Organizador;

import java.time.LocalDate;
import java.time.Period;

public class OrganizadorPerfilResponse {

    private final String nome;
    private final String email;
    private final LocalDate dataNascimento;
    private final String idade;
    private final boolean isAtivo;
    private final Empresa empresa;

    public OrganizadorPerfilResponse(Organizador organizador) {
        this.nome = organizador.getNome();
        this.email = organizador.getEmail();
        this.dataNascimento = organizador.getDataNascimento();
        this.idade = calcularIdade(organizador.getDataNascimento());
        this.isAtivo = organizador.getIsAtivo();

        this.empresa = organizador.getEmpresa();
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

    public  Empresa getEmpresa(){return empresa;}

}