package br.com.softhouse.dende.modules.usuarios.dto;

import java.time.LocalDate;

public record CriarUsuarioRequestDTO(
        String nome,
        LocalDate dataNascimento,
        String sexo,
        String email,
        String senha,
        Boolean isAtivo
){ }
