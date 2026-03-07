package br.com.softhouse.dende.modules.organizadores.dto;

import br.com.softhouse.dende.modules.empresas.model.Empresa;

import java.time.LocalDate;

public record AtualizarOrganizadorRequestDTO (
        String nome,
        LocalDate dataNascimento,
        String sexo,
        String email,
        Empresa empresa,
        String senha,
        Boolean isAtivo,
        Boolean hasEvento
){ }
