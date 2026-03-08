package br.com.softhouse.dende.modules.usuarios.dto;

import java.time.LocalDate;

public record UsuarioListagemResponseDTO (
        String nome,
        String email,
        Boolean isAtivo
) { }
