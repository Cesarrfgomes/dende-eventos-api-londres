package br.com.softhouse.dende.model;

import java.time.LocalDate;
import java.util.Objects;

public class Organizador {

    private Long id;
    private String nome;
    private LocalDate dataNascimento;
    private String sexo;
    private String email;
    private Empresa empresa;
    private String senha;
    private Boolean isAtivo;
    private Boolean hasEvento;

    public Organizador(String nome, LocalDate dataNascimento, String sexo, String email, Empresa empresa, String senha) {
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.sexo = sexo;
        this.email = email;
        this.empresa = empresa;
        this.senha = senha;
        this.isAtivo = true;
        this.hasEvento = false;
    }

    public Organizador() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Empresa getEmpresa(){return empresa;}

    public void setempresa(Empresa empresa){this.empresa = empresa;}

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSenha() {
        return this.senha;
    }

    public void setIsAtivo(Boolean isAtivo) { this.isAtivo = isAtivo; }

    public Boolean getIsAtivo() { return this.isAtivo; }

    public Boolean getHasEvento() {return this.hasEvento;}

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Organizador that = (Organizador) object;
        return Objects.equals(nome, that.nome) && Objects.equals(dataNascimento, that.dataNascimento) && Objects.equals(sexo, that.sexo) && Objects.equals(email, that.email) && Objects.equals(empresa, that.empresa);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, dataNascimento, sexo, email, empresa);
    }

    @Override
    public String toString() {
        return "Organizador{" +
                "nome='" + nome + '\'' +
                ", dataNascimento=" + dataNascimento +
                ", sexo='" + sexo + '\'' +
                ", email='" + email + '\'' +
                ", empresa='" + empresa + '\'' +
                '}';
    }
}
