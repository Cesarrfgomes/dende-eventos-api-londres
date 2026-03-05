package br.com.softhouse.dende.modules.organizadores.dto;

import br.com.softhouse.dende.modules.empresas.model.Empresa;
import br.com.softhouse.dende.modules.organizadores.model.Organizador;

import java.time.LocalDate;
import java.time.Period;

public record OrganizadorDTO(
        long id,
        String nome,
        String email,
        LocalDate dataNascimento,
        String idade,
        String sexo,
        boolean isAtivo,
        Empresa empresa
) {
}