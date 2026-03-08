package br.com.softhouse.dende.modules.organizadores.dto;

import br.com.softhouse.dende.modules.empresas.model.Empresa;

import java.time.LocalDate;

public record OrganizadorListagemResponseDTO (
        String nome,
        String email,
        Empresa empresa,
        Boolean isAtivo,
        Boolean hasEvento
){}
